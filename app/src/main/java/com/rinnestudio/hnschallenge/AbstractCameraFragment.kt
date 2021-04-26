package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.Executors
abstract class AbstractCameraFragment : Fragment() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraPreviewView: PreviewView
    private lateinit var buttonTakePhoto: ImageView
    private lateinit var imageCapture: ImageCapture
    protected lateinit var pathToPhoto: String
    private lateinit var cameraProvider: ProcessCameraProvider

    private val activityPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        initCamera()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraPreviewView = view.findViewById(R.id.cameraPreviewView)
        buttonTakePhoto = view.findViewById(R.id.takePhotoImageView)
        PermissionManager().requestCameraPermission(activityPermission)
    }

    private fun initCamera() {
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
        val result = MutableLiveData<Boolean>()
        result.observe(viewLifecycleOwner) {
            if (it) {
                savingPhoto()
            }
        }

        pathToPhoto = requireContext().filesDir.path + "/img1.jpeg"
        val imageFile = File(pathToPhoto)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()

        buttonTakePhoto.isClickable = true
        buttonTakePhoto.setOnClickListener {
            imageCapture.takePicture(
                outputFileOptions,
                Executors.newSingleThreadExecutor(),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        result.postValue(true)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.i("Log_tag", "Error: ${exception.message}")
                    }
                })
        }
    }

    abstract fun savingPhoto()
}