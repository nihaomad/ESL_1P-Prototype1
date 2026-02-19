package com.example.esl_1p.ui.theme

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ESL_1PHome(
    onAboutClick: () -> Unit,
    onDetectionClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onFeedbackClick: () -> Unit = {}
) {
    // Context and State for Back Press Logic
    val context = LocalContext.current
    val activity = (context as? Activity)
    var backPressedTime by remember { mutableStateOf(0L) }

    // This handles the physical back button on the Home Screen
    BackHandler {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            activity?.finish() // Exit the app
        } else {
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header Section
            Icon(
                imageVector = Icons.Default.BackHand,
                contentDescription = "E-Sign Lingo Logo",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "E-Sign Lingo",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Buttons Section
            Column(
                modifier = Modifier.widthIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MenuButton("Start Sign Detection", Icons.Default.CameraAlt, true, onDetectionClick)
                MenuButton("History", Icons.Default.History, false, onHistoryClick)
                MenuButton("Help", Icons.Default.HelpOutline, false, onHelpClick)
                MenuButton("About", Icons.Default.Info, false, onAboutClick)
                MenuButton("Feedback", Icons.Default.Feedback, false, onFeedbackClick)
            }
        }
    }
}

@Composable
fun MenuButton(text: String, icon: ImageVector, isPrimary: Boolean, onClick: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    if (isPrimary) {
        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(icon, null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    } else {
        OutlinedButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onClick()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(icon, null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    }
}