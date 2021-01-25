package com.dedstudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.adding_challenge_fragment.view.*

class CreateChallengeCameraFragment : Camera() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.adding_challenge_fragment, container, false)
        cameraPreviewView = view.camera_preview_view
        buttonTakePhoto = view.image_button_take_photo
        return view
    }


    override fun savingPhoto() {
        val navDirections =
            CreateChallengeCameraFragmentDirections.actionCreateChallengeNavigationItemToCropChallengePhotoFragment2(
                pathToPhoto
            )
        findNavController().navigate(navDirections)

    }
}