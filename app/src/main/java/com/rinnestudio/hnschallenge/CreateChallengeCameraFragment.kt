package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class CreateChallengeCameraFragment : AbstractCameraFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.adding_challenge_fragment, container, false)
    }


    override fun savingPhoto() {
        val navDirections =
            CreateChallengeCameraFragmentDirections.actionCreateChallengeNavigationItemToCropChallengePhotoFragment(
                pathToPhoto)
        findNavController().navigate(navDirections)

    }
}