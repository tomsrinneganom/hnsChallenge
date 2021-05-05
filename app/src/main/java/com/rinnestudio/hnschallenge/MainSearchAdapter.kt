package com.rinnestudio.hnschallenge

import android.view.View
import androidx.navigation.findNavController

class MainSearchAdapter(profileList: List<Profile>) : AbstractProfileListAdapter(profileList) {

    override fun navigateToOtherProfile(profile: Profile, view: View) {
            val navDirections =
                MainSearchFragmentDirections.actionMainSearchNavigationItemToOtherProfileFragment(
                    profile,null
                )
            view.findNavController().navigate(navDirections)
    }

}