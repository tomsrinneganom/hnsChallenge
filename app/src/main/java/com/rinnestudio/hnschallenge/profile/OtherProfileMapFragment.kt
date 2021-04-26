package com.rinnestudio.hnschallenge.profile

import androidx.fragment.app.viewModels
import com.rinnestudio.hnschallenge.Challenge
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherProfileMapFragment : AbstractProfileMapFragment() {
    private val viewModel: OtherProfileMapViewModel by viewModels({ requireParentFragment() })

    override fun openSelectedChallengeList(selectedChallenges: Array<Challenge>) {
        val navDirections =
            OtherProfileFragmentDirections.actionOtherProfileFragmentToSelectedChallengeListFragment(
                selectedChallenges)
        navigate(navDirections)
    }

    override fun openChallengeList() {
       if(challenges.value != null) {
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