package com.example.esl_1p

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {

    BackHandler {
        onBack() // This triggers logic to set state back to "home" instead of closing the app.
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("About E-Sign Lingo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Version Info
            item {
                AboutInfoCard(
                    title = "Version",
                    subtitle = "1.0.0-alpha (Prototype ESL_1P)",
                    icon = Icons.Default.Info
                )
            }

            // Section 2: Project Description
            item {
                Text(
                    text = "The Project",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "E-Sign Lingo is a sign language detection platform designed to bridge the communication gap. This prototype uses real-time hand tracking to interpret signs accurately.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Section 3: Tech Stack
            item {
                AboutInfoCard(
                    title = "Developer",
                    subtitle = "By group 5 Built with Jetpack Compose & MediaPipe",
                    icon = Icons.Default.Code
                )
            }

            // Section 4: Legal/Terms (Placeholder)
            item {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Privacy Policy", fontWeight = FontWeight.Bold)
                        Text("Usage of camera data is processed locally on-device.", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun AboutInfoCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.labelMedium)
                Text(text = subtitle, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
