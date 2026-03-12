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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    // --- State Management ---
    var selectedLanguage by remember { mutableStateOf("English") }
    var expanded by remember { mutableStateOf(false) }

    // --- Content Mapping ---
    val isEnglish = selectedLanguage == "English"

    val faqs = if (isEnglish) {
        listOf(
            FAQItem("How to get accurate translations?", "For the best results, ensure you are in a well-lit area. Avoid having bright lights directly behind you."),
            FAQItem("Where should I place my hands?", "Keep your hands approximately 1 to 2 feet away from the camera. Ensure your entire hand is visible."),
            FAQItem("The app isn't detecting my sign?", "Try to perform the sign at a steady pace. Fast or blurry movements can be missed."),
            FAQItem("Does it support all sign languages?", "No, ESL_1P is in a prototype phase and only supports Filipino Sign Language (FSL) basics.")
        )
    } else {
        listOf(
            FAQItem("Paano makakuha ng tumpak na salin?", "Para sa pinakamagandang resulta, siguraduhing maliwanag ang paligid. Iwasan ang matinding ilaw sa iyong likuran."),
            FAQItem("Saan dapat ilagay ang mga kamay?", "Ilayo ang kamay ng 1 hanggang 2 talampakan mula sa camera. Siguraduhing kitang-kita ang buong kamay."),
            FAQItem("Bakit hindi nadedetect ang sign ko?", "Subukang gawin ang sign sa katamtamang bilis. Maaaring hindi mabasa ang masyadong mabilis o malabong galaw."),
            FAQItem("Suportado ba nito ang lahat ng sign language?", "Hindi, ang ESL_1P ay nasa prototype phase pa lamang at tanging Filipino Sign Language (FSL) basics ang suportado.")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEnglish) "Help & Guide" else "Tulong at Gabay") },
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
            // Language Selector Section
            item {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    OutlinedTextField(
                        value = selectedLanguage,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Language / Wika") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("English") },
                            onClick = { selectedLanguage = "English"; expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Filipino") },
                            onClick = { selectedLanguage = "Filipino"; expanded = false }
                        )
                    }
                }
            }

            item {
                Text(
                    text = if (isEnglish) "Quick Tips for Success" else "Mga Tip para sa Tagumpay",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TipCard(
                        Modifier.weight(1f),
                        if (isEnglish) "Good Lighting" else "Tamang Ilaw",
                        Icons.Default.Lightbulb
                    )
                    TipCard(
                        Modifier.weight(1f),
                        if (isEnglish) "Clear View" else "Malinaw na Tanaw",
                        Icons.Default.TipsAndUpdates
                    )
                }
            }

            item {
                Text(
                    text = if (isEnglish) "Frequently Asked Questions" else "Mga Karaniwang Tanong",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

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