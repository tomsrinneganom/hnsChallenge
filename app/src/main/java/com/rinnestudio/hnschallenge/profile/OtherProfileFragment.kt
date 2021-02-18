package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherProfileFragment : AbstractProfileFragment() {

    private val viewModel: OtherProfileViewModel by viewModels()
    private val mapViewModel: OtherProfileMapViewModel by viewModels()

    private lateinit var buttonViewSubscribe: MaterialButton
    private lateinit var buttonViewUnsubscribe: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.other_profile_fragment, container, false)
        usernameTextView = view.findViewById(R.id.textViewOtherProfileUserName)
        subscribersTextView = view.findViewById(R.id.textViewOtherProfileSubscribers)
        subscriptionTextView = view.findViewById(R.id.textViewOtherProfileSubscriptions)
        scoreTextView = view.findViewById(R.id.textViewOtherProfileScore)
        photoProfileImageView = view.findViewById(R.id.imageViewOtherProfilePhoto)
        buttonViewUnsubscribe = view.findViewById(R.id.buttonViewOtherProfileUnsubscribe)
        buttonViewSubscribe = view.findViewById(R.id.buttonViewOtherProfileSubscribe)
        gettingProfile()
        checkSubscribe()
        buttonViewUnsubscribe.setOnClickListener {
            viewModel.subscribe(profile, false)
            updateSubscribeButton(false)
            updateUI()
        }
        buttonViewSubscribe.setOnClickListener {
            viewModel.subscribe(profile, true)
            updateSubscribeButton(true)
            //TODO()
            updateUI()
        }

        view.findViewById<TextView>(R.id.textViewOtherProfileSubscriptionsTitle).apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscriptionList()
            }
        }
        subscriptionTextView.setOnClickListener {
            navigateToSubscriptionList()
        }

        view.findViewById<TextView>(R.id.textViewOtherProfileSubscribersTitle).apply {
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
    //TODO()
    override fun gettingProfile() {
        val args: OtherProfileFragmentArgs by navArgs()
        profile = args.profile
        initMap()
        updateUI()
    }
    private fun initMap(){
        mapViewModel.profileId.value = profile.id
    }

    override fun navigateToSubscribersList() {
        val subscribersIdList = profile.subscribers
        if (subscribersIdList.isNotEmpty()) {
            val navDirections =
                OtherProfileFragmentDirections.actionOtherProfileFragmentToSubscriptionsListFragment(
                    subscribersIdList.toTypedArray()
                )
            findNavController().navigate(navDirections)
        }
    }

    override fun navigateToSubscriptionList() {
        val subscriptionsIdList = profile.subscription
        if (subscriptionsIdList.isNotEmpty()) {
            val navDirections =
                OtherProfileFragmentDirections.actionOtherProfileFragmentToSubscriptionsListFragment(
                    subscriptionsIdList.toTypedArray()
                )
            findNavController().navigate(navDirections)
        }
    }

    private fun checkSubscribe() {
        val ownId = Firebase.auth.uid
        if (profile.subscribers.contains(ownId)) {
            updateSubscribeButton(true)
        } else {
            updateSubscribeButton(false)
        }
    }


    private fun updateSubscribeButton(subscribe: Boolean) {
        Log.i("Log_tag", "$subscribe")
        if (subscribe) {
            buttonViewSubscribe.visibility = View.INVISIBLE
            buttonViewUnsubscribe.visibility = View.VISIBLE
        } else {
            buttonViewSubscribe.visibility = View.VISIBLE
            buttonViewUnsubscribe.visibility = View.INVISIBLE
        }
    }


}