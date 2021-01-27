package com.rinnestudio.hnschallenge

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

class DisplayPhotoFragment : Fragment() {

    private val viewModel by viewModels<DisplayImageViewModel>()
    private val args: DisplayPhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.display_image_fragment, container, false)
        val pathToImage = args.pathToPhoto

        val bitmap = BitmapFactory.decodeFile(pathToImage)
        val imageView: ImageView = view.findViewById(R.id.imageView2)
        imageView.setImageBitmap(bitmap)
//        viewModel.createChallenge(pathToPhoto)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}