package com.example.esl_1p

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.esl_1p.ui.HelpScreen
import com.example.esl_1p.ui.theme.ESL_1PHome
import com.example.esl_1p.ui.theme.ESL_1PTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ESL_1PTheme {
                // 1. Create the "Toggle Switch" state
                var currentScreen by remember { mutableStateOf("home") }

                // 2. Navigation Logic
                when (currentScreen) {
                    "home" -> {
                        ESL_1PHome(
                            onAboutClick = { currentScreen = "about" },
                            onFeedbackClick = { currentScreen = "feedback" },
                            onHelpClick = { currentScreen = "help" }
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