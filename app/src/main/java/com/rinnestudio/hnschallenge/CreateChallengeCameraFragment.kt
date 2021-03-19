package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController

class CreateChallengeCameraFragment : AbstractCameraFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.adding_challenge_fragment, container, false)
        layout = view as ConstraintLayout
        cameraPreviewView = view.findViewById(R.id.camera_preview_view)
        buttonTakePhoto = view.findViewById(R.id.image_button_take_photo)
        return view
    }


    override fun savingPhoto() {
        val navDirections =
            CreateChallengeCameraFragmentDirections.actionCreateChallengeNavigationItemToCropChallengePhotoFragment(
                pathToPhoto)
        findNavController().navigate(navDirections)

    }
}