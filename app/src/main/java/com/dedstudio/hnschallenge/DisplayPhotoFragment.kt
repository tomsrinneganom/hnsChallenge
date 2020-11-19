package com.dedstudio.hnschallenge

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.display_image_fragment.*
import java.io.File

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
//        viewModel.addingChallenge(pathToPhoto)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}