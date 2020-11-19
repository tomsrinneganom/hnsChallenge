package com.dedstudio.hnschallenge

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import java.io.ByteArrayOutputStream
import java.io.File

class CropChallengePhotoFragment : AbstractCropPhotoFragment() {
    private val viewModel: CreateChallengeViewModel by viewModels()
    private lateinit var pathToPhoto: String

    override fun setUpCropView() {
        val args: CropChallengePhotoFragmentArgs by navArgs()
        pathToPhoto = args.pathToPhoto
        val photoBitmap = BitmapFactory.decodeFile(pathToPhoto)
        cropImageView.apply {
            setMinCropResultSize(200, 200)
            setImageBitmap(photoBitmap)
        }
    }

    override fun savingPhoto() {
        viewModel.addingChallenge(cropImageView.croppedImage)
        findNavController().apply {
            popBackStack()
            navigateUp()
        }
    }
}