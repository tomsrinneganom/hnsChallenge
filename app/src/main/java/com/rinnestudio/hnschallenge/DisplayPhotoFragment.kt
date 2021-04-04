package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.rinnestudio.hnschallenge.utils.ImageUtils

class DisplayPhotoFragment : Fragment() {

    private val viewModel by viewModels<DisplayImageViewModel>()
    private val args: DisplayPhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.display_image_fragment, container, false)
        val pathToImage = args.firebasePhotoReference

        val imageView: ImageView = view.findViewById(R.id.imageView2)
        ImageUtils().uploadChallengePhotoIntoImageView(pathToImage, imageView)
//        viewModel.createChallenge(pathToPhoto)
        return view
    }

    private fun setPhoto() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}