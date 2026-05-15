package com.adam0006.cinelist.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingsDataStore(private val context: Context) {
    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    private val IS_LIST_LAYOUT = booleanPreferencesKey("is_list_layout")

    val isDarkMode = context.dataStore.data.map { it[IS_DARK_MODE] ?: false }
    val isListLayout = context.dataStore.data.map { it[IS_LIST_LAYOUT] ?: true }

    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { it[IS_DARK_MODE] = isDark }
    }

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { it[IS_LIST_LAYOUT] = isList }
    }
}