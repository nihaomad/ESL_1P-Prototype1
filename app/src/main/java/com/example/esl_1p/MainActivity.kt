package com.example.esl_1p

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.esl_1p.ui.HelpScreen
import com.example.esl_1p.ui.theme.DetectionScreen
import com.example.esl_1p.ui.theme.ESL_1PHome
import com.example.esl_1p.ui.theme.ESL_1PTheme
import com.example.esl_1p.ui.theme.ModeSelectionScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ESL_1PTheme {
                // 1. Navigation and Mode State
                var currentScreen by remember { mutableStateOf("home") }
                var selectedMode by remember { mutableStateOf("") } // phrase or fingerspelling

                // 2. Navigation Logic
                when (currentScreen) {
                    "home" -> {
                        ESL_1PHome(
                            // Now takes you to the mode selection first
                            onDetectionClick = { currentScreen = "mode_selection" },
                            onAboutClick = { currentScreen = "about" },
                            onFeedbackClick = { currentScreen = "feedback" },
                            onHelpClick = { currentScreen = "help" }
                        )
                    }
                    "mode_selection" -> {
                        ModeSelectionScreen(
                            onBack = { currentScreen = "home" },
                            onSelectPhrase = {
                                selectedMode = "phrase"
                                currentScreen = "detection"
                            },
                            onSelectFingerSpelling = {
                                selectedMode = "fingerspelling"
                                currentScreen = "detection"
                            }
                        )
                    }
                    "detection" -> {
                        DetectionScreen(
                            initialMode = selectedMode, // Change 'mode' to 'initialMode'
                            onBack = { currentScreen = "mode_selection" }
                        )
                    }
                    "about" -> {
                        AboutScreen(onBack = { currentScreen = "home" })
                    }
                    "feedback" -> {
                        FeedbackScreen(onBack = { currentScreen = "home" })
                    }
                    "help" -> {
                        HelpScreen(onBack = { currentScreen = "home" })
                    }
                }
            }
        }
    }
}