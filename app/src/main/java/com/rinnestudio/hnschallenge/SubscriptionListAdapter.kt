package com.rinnestudio.hnschallenge

import android.view.View
import androidx.navigation.findNavController
import com.rinnestudio.hnschallenge.profile.SubscriptionsListFragmentDirections

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