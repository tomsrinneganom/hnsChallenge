package com.rinnestudio.hnschallenge

import android.util.Log
import android.widget.ImageView
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.Executors

abstract class AbstractCameraFragment : Fragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    protected lateinit var cameraPreviewView: PreviewView
    protected lateinit var buttonTakePhoto: ImageView
    private lateinit var imageCapture: ImageCapture
    protected lateinit var pathToPhoto: String
    private lateinit var cameraProvider: ProcessCameraProvider
    protected lateinit var layout: ConstraintLayout

    override fun onStart() {
        super.onStart()
        initCamera()
    }

    private fun initCamera() {
        initProcessCameraProvider()
    }

    private fun initProcessCameraProvider() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()
            bindCameraToLifecycle()
            bindActionTakePhoto()

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindCameraToLifecycle() {
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
        preview.setSurfaceProvider(cameraPreviewView.surfaceProvider)

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        imageCapture = ImageCapture.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()

        cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    private fun bindActionTakePhoto() {
        pathToPhoto = requireContext().filesDir.path + "/img1.jpeg"
        val result = MutableLiveData(false)
        val executor = Executors.newSingleThreadExecutor()
        val imageFile = File(pathToPhoto)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()
        buttonTakePhoto.isClickable = true
        buttonTakePhoto.setOnClickListener {
            imageCapture.takePicture(
                outputFileOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        result.postValue(true)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.i("Log_tag", "Error: ${exception.message}")
                    }
                })
        }
        result.observe(viewLifecycleOwner) {
            if (it) {
                savingPhoto()
            }
        }
    }


    abstract fun savingPhoto()
}