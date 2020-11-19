package com.dedstudio.hnschallenge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.FirebaseError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.other_profile_fragment.view.*
import kotlinx.android.synthetic.main.own_profile_fragment.*
import kotlinx.android.synthetic.main.own_profile_fragment.view.*

open class
OwnProfileFragment : AbstractProfileFragment() {

    private val viewModel: OwnProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.own_profile_fragment, container, false)
        profileId = Firebase.auth.uid!!
        usernameTextView = view.textViewOwnProfileUserName
        subscribersTextView = view.textViewOwnProfileSubscribers
        subscriptionTextView = view.textViewOwnProfileSubscriptions
        scoreTextView = view.textViewOwnProfileScore
        photoProfileImageView = view.imageViewOwnProfilePhoto
        Log.i("Log_tag", "profileid: $profileId")

        gettingProfile()

        view.textViewOwnProfileSubscriptionsTitle.apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscriptionList()
            }
        }
        subscriptionTextView.setOnClickListener {
            navigateToSubscriptionList()
        }

        view.textViewOwnProfileSubscribersTitle.apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscribersList()
            }
        }
        subscribersTextView.setOnClickListener {
            navigateToSubscribersList()
        }

        return view
    }

    override fun gettingProfile() {
        viewModel.getProfile(profileId).observe(viewLifecycleOwner){
            profile = it
            updateUI()
        }
    }

    override fun navigateToSubscribersList() {
        val subscribersIdList = profile.subscribers
        if(subscribersIdList.isNotEmpty()) {
            val navDirections =
                OwnProfileFragmentDirections.actionOwnProfileNavigationItemToSubscriptionsListFragment(
                    subscribersIdList.toTypedArray()
                )
            findNavController().navigate(navDirections)
        }
    }

    override fun navigateToSubscriptionList() {
        val subscriptionsIdList = profile.subscription
        if (subscriptionsIdList.isNotEmpty()) {
            val navDirections =
                OwnProfileFragmentDirections.actionOwnProfileNavigationItemToSubscriptionsListFragment(
                    subscriptionsIdList.toTypedArray()
                )
            findNavController().navigate(navDirections)
        }
    }

}