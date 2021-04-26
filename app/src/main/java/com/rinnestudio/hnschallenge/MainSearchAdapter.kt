package com.rinnestudio.hnschallenge

import android.view.View
import androidx.navigation.findNavController
import com.rinnestudio.hnschallenge.profile.AbstractProfileListAdapter
import com.rinnestudio.hnschallenge.profile.Profile

class MainSearchAdapter(profileList: List<Profile>) : AbstractProfileListAdapter(profileList) {

    override fun navigateToOtherProfile(profile: Profile, view: View) {
            val navDirections =
                MainSearchFragmentDirections.actionMainSearchNavigationItemToOtherProfileFragment(
                    profile,null
                )
            view.findNavController().navigate(navDirections)
    }

}