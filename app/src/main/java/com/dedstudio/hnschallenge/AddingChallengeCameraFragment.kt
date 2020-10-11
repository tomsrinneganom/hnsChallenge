package com.dedstudio.hnschallenge

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.adding_challenge_fragment.view.*
import kotlinx.coroutines.launch

class AddingChallengeCameraFragment : Camera() {

    private val viewModel: AddingChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.adding_challenge_fragment, container, false)
        cameraPreviewView = view.camera_preview_view
        buttonTakePhoto = view.image_button_take_photo
        return view
    }


    override fun saveImage() {
        val navDirections =
            AddingChallengeCameraFragmentDirections.actionAddingChallengeNavigationItemToDisplayImageFragment(
                pathToImage
            )
        findNavController().navigate(navDirections)
        viewModel.addingChallenge(pathToImage)
    }

}