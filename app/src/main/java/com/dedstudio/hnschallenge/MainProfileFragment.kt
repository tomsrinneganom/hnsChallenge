package com.dedstudio.hnschallenge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_profile_fragment.*

class MainProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MainProfileFragment()
    }

    private val viewModel: MainProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val profile = Firebase.auth.uid?.let { viewModel.getProfile(it) }
        profile?.observe(viewLifecycleOwner) {
            textViewProfileUserName.text = it.userName
            textViewProfileSubscribers.text = it.subscribers.size.toString()
            textViewProfileSubscriptions.text = it.subscription.size.toString()
            textViewProfileScore.text = it.score.toString()
            Log.i("Log_tag","update")
            Glide.with(requireContext())
                .load(it.profilePhotoReference)
                .into(image_view_profile_photo)
        }
        cardViewSubscribe.apply {
            isClickable = true
            setOnClickListener {
                //TODO() subscribeToUser() viewModel method
                //need add to database where saved user data document with list own subscription
                viewModel.subscribeToUser(Firebase.auth.uid!!)
            }
        }
    }


}