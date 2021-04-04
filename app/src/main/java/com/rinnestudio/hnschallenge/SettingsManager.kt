package com.rinnestudio.hnschallenge

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.collect

class SettingsManager() {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
    }

    suspend fun setDarkTheme(context: Context) {
        context.dataStore.edit {
            it[PreferencesKey.DARK_THEME] = true
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    suspend fun setLightTheme(context: Context) {
        context.dataStore.edit {
            it[PreferencesKey.DARK_THEME] = false
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    suspend fun setCurrentTheme(context: Context) {
        context.dataStore.data.collect {
            val currentTheme = it[PreferencesKey.DARK_THEME] ?: false
            if (currentTheme) {
                if (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            } else {
                if (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
    fun getCurrentAppTheme(context: Context)  = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}