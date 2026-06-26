package com.adam0006.cinelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adam0006.cinelist.ui.screen.MainScreen
import com.adam0006.cinelist.ui.screen.theme.CinelistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinelistTheme {
                MainScreen()
            }
        }
    }
}