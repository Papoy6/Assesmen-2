package com.adam0006.cinelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.adam0006.cinelist.database.MainViewModel
import com.adam0006.cinelist.database.ViewModelFactory
import com.adam0006.cinelist.navigation.SetupNavGraph
import com.adam0006.cinelist.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val dataStore = remember { SettingsDataStore(context) }
            val isDarkModePref by dataStore.isDarkMode.collectAsState(initial = isSystemInDarkTheme())
            
            val factory = ViewModelFactory(context)
            val viewModel: MainViewModel = viewModel(factory = factory)
            val navController = rememberNavController()

            MaterialTheme(
                colorScheme = if (isDarkModePref) darkColorScheme() else lightColorScheme()
            ) {
                SetupNavGraph(navController, viewModel)
            }
        }
    }
}
