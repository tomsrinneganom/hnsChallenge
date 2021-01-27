package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.theartofdev.edmodo.cropper.CropImageView

abstract class AbstractCropPhotoFragment : Fragment() {

    protected lateinit var cropImageView: CropImageView
    protected lateinit var savePhotoFloatingActionButton: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.crop_photo_fragment, container, false)
        cropImageView = view.findViewById(R.id.cropImageView)
        setUpCropView()
        savePhotoFloatingActionButton = view.findViewById<FloatingActionButton>(R.id.cropPhotoFloatingButtonSavePhoto).apply {
            setOnClickListener {
                savingPhoto()
            }
        }
        return view
    }
    protected abstract fun setUpCropView()
    protected abstract fun savingPhoto()



}