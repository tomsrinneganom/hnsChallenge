package com.dedstudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.crop_photo_fragment.view.*

abstract class AbstractCropPhotoFragment : Fragment() {

    protected lateinit var cropImageView: CropImageView
    protected lateinit var savePhotoFloatingActionButton: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.crop_photo_fragment, container, false)
        cropImageView = view.cropImageView
        setUpCropView()
        savePhotoFloatingActionButton = view.cropPhotoFloatingButtonSavePhoto.apply {
            setOnClickListener {
                savingPhoto()
            }
        }
        return view
    }
    protected abstract fun setUpCropView()
    protected abstract fun savingPhoto()



}