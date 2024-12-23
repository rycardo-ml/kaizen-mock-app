package com.example.kaizenmockapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.kaizenmockapp.data.SportEvent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val favoritePreference = stringSetPreferencesKey("favorites")

class FavoriteRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun toggleFavorite(event: SportEvent): Boolean {
        val favoritesFlow = dataStore.data.map { preferences ->
            preferences[favoritePreference] ?: emptyList()
        }

        var addToFavorite = true
        val newFavorites = mutableSetOf<String>()
        favoritesFlow.first().forEach {
            if (it == event.id) {
                addToFavorite = false
                return@forEach
            }

            newFavorites.add(it)
        }

        if (addToFavorite) newFavorites.add(event.id)

        dataStore.edit { preferences ->
            preferences[favoritePreference] = newFavorites
        }

        return addToFavorite
    }

    suspend fun getFavorites(): Collection<String> {
        return dataStore.data.map { preferences ->
            preferences[favoritePreference] ?: emptyList()
        }.first()
    }
}
