package com.rinnestudio.hnschallenge.profile

import android.view.View
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.R

class SubscriptionListAdapter(profileList: ArrayList<Profile>) : AbstractProfileListAdapter(profileList) {
    override fun navigateToProfile(profile: Profile, view: View) {
        if (profile.id == Firebase.auth.uid) {
            view.findNavController().navigate(R.id.ownProfileNavigationItem)
        } else {
            val navDirections =
                SubscriptionsListFragmentDirections.actionSubscriptionsListFragmentToOtherProfileFragment(
                    profile,null
                )
            view.findNavController().navigate(navDirections)
        }
    }
}