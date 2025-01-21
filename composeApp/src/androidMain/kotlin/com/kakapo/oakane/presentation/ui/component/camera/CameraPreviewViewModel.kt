package com.kakapo.oakane.presentation.ui.component.camera

import android.content.Context
import android.os.Environment
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class CameraPreviewViewModel : ViewModel() {

    val surfaceRequest get() = _surfaceRequest.asStateFlow()
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
        }
    }

    val imageCapture: ImageCapture = ImageCapture.Builder().build()

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        processCameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, cameraPreviewUseCase, imageCapture)
        try { awaitCancellation() } finally { processCameraProvider.unbindAll() }
    }

    fun captureImage(context: Context) {
        val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDirectory.exists()) downloadsDirectory.mkdirs()

        val imageFile = File(downloadsDirectory, "IMG_${System.currentTimeMillis()}.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Logger.d("Image saved at: ${output.savedUri ?: imageFile.toURI()}")
                }

                override fun onError(exception: ImageCaptureException) {
                    Logger.e( "Image capture failed: ${exception.message}", exception)
                }
            }
        )
    }
}