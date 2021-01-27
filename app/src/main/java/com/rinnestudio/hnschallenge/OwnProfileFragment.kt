package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class OwnProfileFragment : AbstractProfileFragment() {

    private val viewModel: OwnProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.own_profile_fragment, container, false)
        usernameTextView = view.findViewById(R.id.textViewOwnProfileUserName)
        subscribersTextView = view.findViewById(R.id.textViewOwnProfileSubscribers)
        subscriptionTextView = view.findViewById(R.id.textViewOwnProfileSubscriptions)
        scoreTextView = view.findViewById(R.id.textViewOwnProfileScore)
        photoProfileImageView = view.findViewById(R.id.imageViewOwnProfilePhoto)

        gettingProfile()

        view.findViewById<TextView>(R.id.textViewOwnProfileSubscriptionsTitle).apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscriptionList()
            }
        }
        subscriptionTextView.setOnClickListener {
            navigateToSubscriptionList()
        }

        view.findViewById<TextView>(R.id.textViewOwnProfileSubscribersTitle).apply {
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
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            profile = viewModel.getProfile()
            updateUI()
        }
    }

    override fun navigateToSubscribersList() {
        val subscribersIdList = profile.subscribers
        if (subscribersIdList.isNotEmpty()) {
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