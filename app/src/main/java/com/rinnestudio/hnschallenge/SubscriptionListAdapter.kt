package com.rinnestudio.hnschallenge

import android.view.View
import androidx.navigation.findNavController

class SubscriptionListAdapter(profileList: List<Profile>) :
    AbstractProfileListAdapter(profileList) {

    override fun navigateToOtherProfile(profile: Profile, view: View) {
        val navDirections =
            SubscriptionsListFragmentDirections.actionSubscriptionsListFragmentToOtherProfileFragment(
                profile, null
            )
        view.findNavController().navigate(navDirections)
    }

}