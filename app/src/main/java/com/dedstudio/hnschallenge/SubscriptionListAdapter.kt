package com.dedstudio.hnschallenge

import android.view.View
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SubscriptionListAdapter(profileList: ArrayList<Profile>) :ProfileListAdapter(profileList){
    override fun navigateToProfile(profile: Profile, view: View) {
        if (profile.id == Firebase.auth.uid) {
            view.findNavController().navigate(R.id.ownProfileNavigationItem)
        } else {
            val navDirections =
                SubscriptionsListFragmentDirections.actionSubscriptionsListFragmentToOtherProfileFragment(
                    profile
                )
            view.findNavController().navigate(navDirections)
        }
    }
}