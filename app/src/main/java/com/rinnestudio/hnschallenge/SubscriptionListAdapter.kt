package com.rinnestudio.hnschallenge

import android.view.View
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.profile.Profile
import com.rinnestudio.hnschallenge.profile.ProfileListAdapter

class SubscriptionListAdapter(profileList: ArrayList<Profile>) : ProfileListAdapter(profileList){
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