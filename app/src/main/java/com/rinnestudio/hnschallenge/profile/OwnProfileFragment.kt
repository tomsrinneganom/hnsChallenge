package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.rinnestudio.hnschallenge.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class OwnProfileFragment : AbstractProfileFragment() {

    private val viewModel: OwnProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.own_profile_fragment, container, false)
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
            hideMapFragment()
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
            hideMapFragment()
            findNavController().navigate(navDirections)
        }
    }

}