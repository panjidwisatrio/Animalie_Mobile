package com.panji.animalie.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.themeDataStore by preferencesDataStore("theme_preferences")

object ThemePreferences {
    private const val THEME_KEY = "theme_key"

    suspend fun setTheme(context: Context, isDarkTheme: Boolean) {
        context.themeDataStore.edit { preferences ->
            preferences[booleanPreferencesKey(THEME_KEY)] = isDarkTheme
        }
    }

    fun getTheme(context: Context): Flow<Boolean> {
        val themeKey = booleanPreferencesKey(THEME_KEY)
        return context.themeDataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }
}
