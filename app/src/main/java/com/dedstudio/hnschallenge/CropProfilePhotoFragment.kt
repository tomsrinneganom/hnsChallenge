package com.dedstudio.hnschallenge

import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.bumptech.glide.load.model.ResourceLoader

class CropProfilePhotoFragment : AbstractCropPhotoFragment() {
    private val viewModel: SignUpViewModel by activityViewModels()
    override fun setUpCropView() {
        val args: CropProfilePhotoFragmentArgs by navArgs()
        val uri = Uri.parse(args.profilePhotoUri)
        cropImageView.apply {
            setAspectRatio(1, 1)
            setFixedAspectRatio(true)
            setMinCropResultSize(200, 200)
            setImageUriAsync(uri)
        }

    }

    override fun savingPhoto() {
        viewModel.profilePhoto.value = cropImageView.croppedImage
        findNavController().navigateUp()
    }
}