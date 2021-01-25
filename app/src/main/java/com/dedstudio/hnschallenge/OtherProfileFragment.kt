package com.dedstudio.hnschallenge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.other_profile_fragment.view.*

@AndroidEntryPoint
class OtherProfileFragment : AbstractProfileFragment() {

    private val viewModel: OtherProfileViewModel by viewModels()
    private lateinit var buttonViewSubscribe: MaterialButton
    private lateinit var buttonViewUnsubscribe: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.other_profile_fragment, container, false)
        usernameTextView = view.textViewOtherProfileUserName
        subscribersTextView = view.textViewOtherProfileSubscribers
        subscriptionTextView = view.textViewOtherProfileSubscriptions
        scoreTextView = view.textViewOtherProfileScore
        photoProfileImageView = view.imageViewOtherProfilePhoto
        buttonViewUnsubscribe = view.buttonViewOtherProfileUnsubscribe
        buttonViewSubscribe = view.buttonViewOtherProfileSubscribe
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
            updateUI()
        }

        view.textViewOtherProfileSubscriptionsTitle.apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscriptionList()
            }
        }
        subscriptionTextView.setOnClickListener {
            navigateToSubscriptionList()
        }

        view.textViewOtherProfileSubscribersTitle.apply {
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
        val args: OtherProfileFragmentArgs by navArgs()
        profile = args.profile
        updateUI()
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
            buttonViewSubscribe.visibility = View.GONE
            buttonViewUnsubscribe.visibility = View.VISIBLE
        } else {
            buttonViewSubscribe.visibility = View.VISIBLE
            buttonViewUnsubscribe.visibility = View.GONE
        }
    }


}