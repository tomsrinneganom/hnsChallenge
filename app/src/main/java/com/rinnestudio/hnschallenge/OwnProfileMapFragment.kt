package com.rinnestudio.hnschallenge

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class OwnProfileMapFragment : AbstractProfileMapFragment() {
    private val viewModel: OwnProfileMapViewModel by viewModels({ requireParentFragment() })

    override fun initChallengeObserver() {
        challenges = viewModel.getChallenges()
    }

    override fun openSelectedChallengeList(selectedChallenges: Array<Challenge>) {
        if (selectedChallenges.isNotEmpty()) {
            val navDirections =
                OwnProfileFragmentDirections.actionOwnProfileNavigationItemToSelectedChallengeListFragment(
                    selectedChallenges)
            navigate(navDirections)
        }
    }

    override fun openChallengeList() {
        if (challenges.value == null || challenges.value!!.isEmpty()) {
            displayTostForEmptyChallengeList()
        } else {
            val navDirections =
                OwnProfileFragmentDirections.actionOwnProfileNavigationItemToSelectedChallengeListFragment(
                    challenges.value!!.toTypedArray()
                )
            navigate(navDirections)
        }
    }

}