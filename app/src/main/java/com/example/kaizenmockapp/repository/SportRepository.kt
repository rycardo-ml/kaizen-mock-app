package com.example.kaizenmockapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.kaizenmockapp.data.Sport
import com.example.kaizenmockapp.data.SportDto
import com.example.kaizenmockapp.data.SportEventStorage
import com.example.kaizenmockapp.data.toDomain
import com.example.kaizenmockapp.data.toStorage
import com.example.kaizenmockapp.network.KtorClient
import com.example.kaizenmockapp.util.KaizenResult
import com.example.kaizenmockapp.util.SerializationManager
import com.example.kaizenmockapp.util.wrapToResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

private val sportsPreference = stringSetPreferencesKey("sports")


class SportRepository @Inject constructor(
    private val httpClient: KtorClient,
    private val dataStore: DataStore<Preferences>,
    private val serializationManager: SerializationManager,
    private val eventRepository: EventRepository,
) {

    fun observeSports() : Flow<KaizenResult<List<Sport>>> {
        //FIXME check if can happen error here
        val flowSports = dataStore.data.map { preferences ->
            preferences[sportsPreference]
        }.distinctUntilChanged()

        return flowSports.mapNotNull { sportsJson ->
            if (sportsJson == null) {
                fetchSportsAndEvents()
                return@mapNotNull null
            }

            //delay(5000)

            val sportsStorage = sportsJson.map { sportJson ->
                serializationManager.stringToSport(sportJson)
            }

            return@mapNotNull sportsStorage.map { it.toDomain() }
        }.wrapToResult()
    }

    private suspend fun fetchSportsAndEvents() {
        val sportsDto = serializationManager.deserializeSports(httpClient.fetchSportsAndEvents())
        //val sportsDto = emptyList<SportDto>() //serializationManager.deserializeSports(httpClient.fetchSportsAndEvents())

        val eventsToStorage = mutableMapOf<String, List<SportEventStorage>>()
        val sportsToStorage = sportsDto.map { sportDto ->
            eventsToStorage[sportDto.id] = sportDto.events.map { it.toStorage() }
            sportDto.toStorage()
        }

        dataStore.edit { preferences ->
            val sportsJson = serializationManager.sportsToString(sportsToStorage)
            preferences[sportsPreference] = sportsJson.toSet()
        }

        eventRepository.saveEvents(eventsToStorage)
    }
}
