package com.adam0006.cinelist.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceManager(private val context: Context) {

    companion object {
        val SHOW_LIST_KEY = booleanPreferencesKey("show_list")
    }

    val showListFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHOW_LIST_KEY] ?: true
        }

    suspend fun saveLayoutPreference(showList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_LIST_KEY] = showList
        }
    }
}