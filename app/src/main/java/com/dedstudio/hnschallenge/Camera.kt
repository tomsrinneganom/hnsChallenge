package com.dedstudio.hnschallenge

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.camera.core.*
import androidx.camera.core.impl.PreviewConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BitmapCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.google.common.util.concurrent.ListenableFuture
import com.mapbox.mapboxsdk.utils.BitmapUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.Executors

abstract class Camera : Fragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    protected lateinit var cameraPreviewView: PreviewView
    protected lateinit var buttonTakePhoto: ImageView
    private lateinit var imageCapture: ImageCapture
    protected lateinit var pathToImage: String
    protected val savedImage = MutableLiveData<Boolean>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedImage.observe(viewLifecycleOwner) {
            saveImage()
        }
        initCamera()
    }

    private fun initCamera() {
        initProcessCameraProvider()
    }

    private fun initProcessCameraProvider() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
            bindTakePhoto()
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        imageCapture = ImageCapture.Builder()
            .setTargetResolution(Size(400, 400))
            .build()

        preview.setSurfaceProvider(cameraPreviewView.createSurfaceProvider())

        val camera = cameraProvider.bindToLifecycle(
            viewLifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    private fun bindTakePhoto() {
        pathToImage = requireContext().filesDir.path + "/img1.jpeg"
        val imageFile = File(pathToImage)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()
        var f = false
        buttonTakePhoto.isClickable = true
        buttonTakePhoto.setOnClickListener {
            imageCapture.takePicture(
                outputFileOptions,
                Executors.newSingleThreadExecutor(),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        savedImage.postValue(true)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.i("Log_tag", "Error: ${exception.message}")
                    }
                })
        }
    }
    abstract fun saveImage()
}