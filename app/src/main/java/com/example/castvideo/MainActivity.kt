package com.example.castvideo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import com.example.castvideo.ui.theme.CastVideoTheme
import com.example.ui.screen.CastVideoScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CastVideoTheme {
                Scaffold { innerPadding ->
                    CastVideoScreen(innerPadding = innerPadding)
                }
            }
        }
    }
}