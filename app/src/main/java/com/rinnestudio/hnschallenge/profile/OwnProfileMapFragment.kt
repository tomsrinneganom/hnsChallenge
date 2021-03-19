package com.rinnestudio.hnschallenge.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rinnestudio.hnschallenge.Challenge
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class OwnProfileMapFragment : AbstractProfileMapFragment() {
    private val viewModel: OwnProfileMapViewModel by viewModels({ requireParentFragment() })

    override suspend fun getChallenges() {
            challenges = viewModel.getChallenges()
    }

    override fun openSelectedChallengeList(selectedChallenges: Array<Challenge>) {
        val navDirections =
            OwnProfileFragmentDirections.actionOwnProfileNavigationItemToSelectedChallengeListFragment(
                selectedChallenges)
        mapView.visibility = View.INVISIBLE
        findNavController().navigate(navDirections)

    }

    override fun openChallengeList() {
        val navDirections =
            OwnProfileFragmentDirections.actionOwnProfileNavigationItemToSelectedChallengeListFragment(
                challenges.toTypedArray()
            )
        mapView.visibility =View.INVISIBLE
        findNavController().navigate(navDirections)
    }

}