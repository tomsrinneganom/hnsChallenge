package com.rinnestudio.hnschallenge

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        viewLifecycleOwner.lifecycleScope.launch {
            val result = viewModel.createChallenge(cropImageView.croppedImage)
            Toast.makeText(requireContext(), "$result", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.mainMapNavigationItem)
        }
        Toast.makeText(requireContext(), "result", Toast.LENGTH_LONG).show()
    }
}