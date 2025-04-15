package com.asuni.zenithra.ui.dashboard.face_detection

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.RectF
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.asuni.zenithra.util.openAppSettings
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

@Composable
fun CameraPermissionWithFaceDetections() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var permissionGranted by remember { mutableStateOf(false) }

    // Permission check function
    fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Launch permission dialog
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
    }

    // Initial permission check
    LaunchedEffect(Unit) {
        permissionGranted = checkPermission()
        if (!permissionGranted) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Re-check permission when returning from app settings
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                permissionGranted = checkPermission()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (permissionGranted) {
        FaceDetectionScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Camera permission is required")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                openAppSettings(context)
            }) {
                Text("Grant Permission")
            }
        }
    }
}


@Composable
fun FaceDetectionScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    var faceBounds by remember { mutableStateOf<RectF?>(null) }
    var previewWidth by remember { mutableStateOf(0f) }
    var previewHeight by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val detector = FaceDetection.getClient(
                        FaceDetectorOptions.Builder()
                            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
                            .build()
                    )

                    val analysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    analysis.setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val rotation = imageProxy.imageInfo.rotationDegrees
                            val inputImage = InputImage.fromMediaImage(mediaImage, rotation)

                            detector.process(inputImage)
                                .addOnSuccessListener { faces ->
                                    if (faces.isNotEmpty() && previewView.width > 0 && previewView.height > 0) {
                                        val face = faces[0]
                                        val box = face.boundingBox

                                        previewWidth = previewView.width.toFloat()
                                        previewHeight = previewView.height.toFloat()

                                        val imageWidth = inputImage.width.toFloat()
                                        val imageHeight = inputImage.height.toFloat()

                                        val scaleX = previewWidth / imageWidth
                                        val scaleY = previewHeight / imageHeight

                                        val left = box.left * scaleX
                                        val top = box.top * scaleY
                                        val right = box.right * scaleX
                                        val bottom = box.bottom * scaleY

                                        val mirroredLeft = previewWidth - right
                                        val mirroredRight = previewWidth - left

                                        faceBounds = RectF(
                                            mirroredLeft,
                                            top,
                                            mirroredRight,
                                            bottom
                                        )

                                    } else {
                                        faceBounds = null
                                    }
                                }
                                .addOnFailureListener {
                                    faceBounds = null
                                }
                                .addOnCompleteListener {
                                    imageProxy.close()
                                }
                        } else {
                            imageProxy.close()
                        }
                    }

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_FRONT_CAMERA,
                        preview,
                        analysis
                    )
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        // Draw rectangles
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerBoxWidth = 400f
            val centerBoxHeight = 400f
            val centerLeft = (size.width - centerBoxWidth) / 2
            val centerTop = (size.height - centerBoxHeight) / 2
            val centerRect = RectF(
                centerLeft,
                centerTop,
                centerLeft + centerBoxWidth,
                centerTop + centerBoxHeight
            )
            val isInside = faceBounds?.let { face ->
                centerRect.contains(face.centerX(), face.centerY())
            } == true

            // Draw center reference box
            drawRect(
                color = if (isInside) Color.Green else Color.Red,
                topLeft = Offset(centerRect.left, centerRect.top),
                size = Size(centerRect.width(), centerRect.height()),
                style = Stroke(width = 5f)
            )
        }
    }
}
