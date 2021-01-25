package com.dedstudio.hnschallenge

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.concurrent.thread

abstract class Camera : Fragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    protected lateinit var cameraPreviewView: PreviewView
    protected lateinit var buttonTakePhoto: ImageView
    private lateinit var imageCapture: ImageCapture
    protected lateinit var pathToPhoto: String
    private val savingImageLiveData = MutableLiveData<Boolean>()
    protected lateinit var cameraProvider: ProcessCameraProvider

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savingImageLiveData.observe(viewLifecycleOwner) {
            savingPhoto()
        }
        initCamera()
    }

    private fun initCamera() {
        initProcessCameraProvider()
    }

    private fun initProcessCameraProvider() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindPreview()
            viewLifecycleOwner.lifecycleScope.launch {
                bindTakePhoto()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview() {
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        imageCapture = ImageCapture.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()

        preview.setSurfaceProvider(cameraPreviewView.createSurfaceProvider())

        val camera = cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

    }

    private suspend fun bindTakePhoto(): Boolean {
        pathToPhoto = requireContext().filesDir.path + "/img1.jpeg"
        val executor = Executors.newSingleThreadExecutor()
        val imageFile = File(pathToPhoto)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()
        var f = false
        buttonTakePhoto.isClickable = true
        buttonTakePhoto.setOnClickListener {
            imageCapture.takePicture(
                outputFileOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        f = true
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.i("Log_tag", "Error: ${exception.message}")
                    }
                })
        }
        return f
    }


    abstract fun savingPhoto()

    override fun onStop() {
        super.onStop()
        cameraProvider.unbindAll()

    }
}