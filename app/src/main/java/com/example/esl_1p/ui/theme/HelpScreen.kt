package com.example.esl_1p.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(onBack: () -> Unit) {
    BackHandler {
        onBack() // This triggers logic to set state back to "home" instead of closing the app.
    }

    val faqs = listOf(
        FAQItem(
            "How to get accurate translations?",
            "For the best results, ensure you are in a well-lit area. Avoid having bright lights directly behind you (like a window), as this creates a silhouette and makes it hard for the AI to see your hand details."
        ),
        FAQItem(
            "Where should I place my hands?",
            "Keep your hands approximately 1 to 2 feet away from the camera. Ensure your entire hand—from wrist to fingertips—is visible within the camera frame."
        ),
        FAQItem(
            "The app isn't detecting my sign?",
            "Try to perform the sign at a steady pace. Fast or blurry movements can sometimes be missed by the current prototype. Make sure your background isn't too cluttered."
        ),
        FAQItem(
            "Does it support all sign languages?",
            "No, ESL_1P is in a prototype phase and will only support Filipino Sign Language (FSL) basics."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Guide") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Quick Tips for Success",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Quick Tips Cards
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TipCard(Modifier.weight(1f), "Good Lighting", Icons.Default.Lightbulb)
                    TipCard(Modifier.weight(1f), "Clear View", Icons.Default.TipsAndUpdates)
                }
            }

            item {
                Text(
                    text = "Frequently Asked Questions",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            // FAQ List
            items(faqs) { faq ->
                FAQCard(faq)
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun TipCard(modifier: Modifier, title: String, icon: ImageVector) {
    Card(
        modifier = modifier.height(100.dp), // Added a fixed height so they look uniform
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // This ensures the column fills the card width/height
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // Centers icon and text horizontally
            verticalArrangement = Arrangement.Center // Centers them vertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center, // Centers the text lines
                lineHeight = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun FAQCard(faq: FAQItem) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.QuestionAnswer, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.width(8.dp))
                Text(text = faq.question, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Text(text = faq.answer, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class FAQItem(val question: String, val answer: String)