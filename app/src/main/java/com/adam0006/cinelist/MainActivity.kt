package com.adam0006.cinelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adam0006.miniproject.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataStore = remember { SettingsDataStore(this) }
            val isDarkMode by dataStore.isDarkMode.collectAsState(false)

            MiniProjectTheme(darkTheme = isDarkMode) {
                SetupNavGraph()
            }
        }
    }
}