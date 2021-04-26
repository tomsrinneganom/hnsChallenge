package com.rinnestudio.hnschallenge

import androidx.navigation.fragment.navArgs
import com.rinnestudio.hnschallenge.utils.ChallengeUtils

class SelectedChallengeListFragment : AbstractChallengeListFragment() {

    override fun getChallenges() {
        val args: SelectedChallengeListFragmentArgs by navArgs()
        challenges = args.challenges.toList()
        ChallengeUtils().sortChallengesByDistance(challenges, requireContext()).observe(this) {
            challenges = it
            initViewAdapter()
            bindRecyclerView()
        }

    }

    override fun initViewAdapter() {
        if (!challenges.isNullOrEmpty()) {
            viewAdapter = SelectedChallengeListAdapter(challenges, requireContext())
        }
    }

}