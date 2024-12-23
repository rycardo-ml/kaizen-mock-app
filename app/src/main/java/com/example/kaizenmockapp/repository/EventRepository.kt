package com.example.kaizenmockapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.kaizenmockapp.data.SportEvent
import com.example.kaizenmockapp.data.SportEventStorage
import com.example.kaizenmockapp.data.toDomain
import com.example.kaizenmockapp.util.SerializationManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val serializationManager: SerializationManager,
) {

    suspend fun saveEvents(eventsToStorage: MutableMap<String, List<SportEventStorage>>) {
        dataStore.edit { preferences ->
            eventsToStorage.entries.forEach { entry ->
                val eventPreference = stringPreferencesKey("event_${entry.key}")
                preferences[eventPreference] = serializationManager.sportEventsToString(entry.value)
            }
        }
    }

    suspend fun getEvents(sportId: String): List<SportEvent> {
        val eventPreference = stringPreferencesKey("event_${sportId}")

        val flowEvents = dataStore.data.map { preferences ->
            preferences[eventPreference]
        }

        val eventsString = flowEvents.first() ?: return emptyList()

        val eventsStorage = serializationManager.stringToSportEvents(eventsString)

        return eventsStorage.map {
            it.toDomain()
        }
    }
}