package com.adam0006.miniproject.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingsDataStore(private val context: Context) {
    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    val isDarkMode = context.dataStore.data.map { it[IS_DARK_MODE] ?: false }

    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { it[IS_DARK_MODE] = isDark }
    }
}