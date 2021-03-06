package com.rinnestudio.hnschallenge

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherProfileMapFragment : AbstractProfileMapFragment() {
    private val viewModel: OtherProfileMapViewModel by viewModels({ requireParentFragment() })

    override fun openSelectedChallengeList(selectedChallenges: Array<Challenge>) {
        if (selectedChallenges.isNotEmpty()) {
            val navDirections =
                OtherProfileFragmentDirections.actionOtherProfileFragmentToSelectedChallengeListFragment(
                    selectedChallenges)
            navigate(navDirections)
        }
    }

    override fun openChallengeList() {
        if (challenges.value == null || challenges.value!!.isEmpty()) {
            displayTostForEmptyChallengeList()
        } else {
            val navDirections =
                OtherProfileFragmentDirections.actionOtherProfileFragmentToSelectedChallengeListFragment(
                    challenges.value!!.toTypedArray()
                )
            navigate(navDirections)
        }
    }

    override fun initChallengeObserver() {
        challenges = viewModel.getChallenges()
    }

}