package com.example.kaizenmockapp.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject

class ClearDataUseCase @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun execute() {
        dataStore.edit {
            it.clear()
        }
    }
}