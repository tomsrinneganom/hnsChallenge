package com.rinnestudio.hnschallenge.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rinnestudio.hnschallenge.Challenge
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherProfileMapFragment: AbstractProfileMapFragment() {
    private val viewModel: OtherProfileMapViewModel by viewModels({requireParentFragment()})

    override suspend fun getChallenges() {
        challenges = viewModel.getChallenges()
    }

    override fun openSelectedChallengeList(selectedChallenges: Array<Challenge>) {
        val navDirections =
            OtherProfileFragmentDirections.actionOtherProfileFragmentToSelectedChallengeListFragment(
                selectedChallenges)
        findNavController().navigate(navDirections)

    }
    override fun openChallengeList(){
        val navDirections =
            OtherProfileFragmentDirections.actionOtherProfileFragmentToSelectedChallengeListFragment(
                challenges.toTypedArray()
            )
        findNavController().navigate(navDirections)
    }

}