package com.example.esl_1p.ui.theme

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun DetectionScreen(initialMode: String, onBack: () -> Unit) {
    BackHandler { onBack() }

    var currentMode by remember { mutableStateOf(initialMode) }
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_FRONT) }
    var resultText by remember { mutableStateOf("Waiting for sign...") }

    // Toggle state for detection
    var isDetecting by remember { mutableStateOf(true) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (currentMode == "phrase") "FSL Phrase Mode" else "FSL Finger Spelling") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (cameraPermissionState.status.isGranted) {
                CameraPreviewView(lensFacing = lensFacing)

                // Top Overlay (Result + Mode)
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TranslationResultBox(
                        text = if (isDetecting) resultText else "Detection Paused",
                        isDetecting = isDetecting
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ModeBadge(currentMode)
                }

                // Bottom Overlay (Controls)
                ControlButtons(
                    isDetecting = isDetecting,
                    onToggleDetection = { isDetecting = !isDetecting },
                    onModeSwitch = {
                        currentMode = if (currentMode == "phrase") "fingerspelling" else "phrase"
                        resultText = "Mode changed..."
                    },
                    onCameraFlip = {
                        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_FRONT)
                            CameraSelector.LENS_FACING_BACK else CameraSelector.LENS_FACING_FRONT
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            } else {
                PermissionRequestUI(
                    onRequest = { cameraPermissionState.launchPermissionRequest() },
                    onBack = onBack
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }
}

// --- SUB-COMPONENTS ---

@Composable
fun TranslationResultBox(text: String, isDetecting: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f).heightIn(min = 70.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDetecting) Color.Black.copy(alpha = 0.7f) else Color.DarkGray.copy(alpha = 0.8f)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(12.dp), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                color = if (isDetecting) Color.White else Color.LightGray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ModeBadge(mode: String) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f), shape = RoundedCornerShape(8.dp)) {
        Text(text = if (mode == "phrase") "PHRASE MODE" else "FINGER SPELLING", modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ControlButtons(
    isDetecting: Boolean,
    onToggleDetection: () -> Unit,
    onModeSwitch: () -> Unit,
    onCameraFlip: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 32.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Stop/Start Button
        Button(
            onClick = onToggleDetection,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isDetecting) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Icon(if (isDetecting) Icons.Default.Stop else Icons.Default.PlayArrow, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(if (isDetecting) "Stop Detection" else "Start Detection")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton(onClick = onModeSwitch) {
                Icon(Icons.Default.SwapHoriz, null)
                Spacer(Modifier.width(8.dp))
                Text("Switch Mode")
            }
            LargeFloatingActionButton(onClick = onCameraFlip) {
                Icon(Icons.Default.FlipCameraAndroid, contentDescription = "Flip Camera")
            }
        }
    }
}

@Composable
fun CameraPreviewView(lensFacing: Int) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx -> PreviewView(ctx) },
        modifier = Modifier.fillMaxSize(),
        update = { previewView ->
            val executor = androidx.core.content.ContextCompat.getMainExecutor(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
                val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
                } catch (e: Exception) { e.printStackTrace() }
            }, executor)
        }
    )
}

@Composable
fun PermissionRequestUI(onRequest: () -> Unit, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Camera access is required for FSL detection.", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequest) { Text("Allow Camera") }
        TextButton(onClick = onBack) { Text("Go Back") }
    }
}