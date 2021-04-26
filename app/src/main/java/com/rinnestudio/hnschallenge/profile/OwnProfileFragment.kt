package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rinnestudio.hnschallenge.R
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.getProfile().observe(this){
            profile = it
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
            navigate(navDirections)
        }
    }

    override fun navigateToSubscriptionList() {
        val subscriptionsIdList = profile.subscription
        if (subscriptionsIdList.isNotEmpty()) {
            val navDirections =
                OwnProfileFragmentDirections.actionOwnProfileNavigationItemToSubscriptionsListFragment(
                    subscriptionsIdList.toTypedArray()
                )
            navigate(navDirections)
        }
    }
}