package com.rinnestudio.hnschallenge

import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.rinnestudio.hnschallenge.utils.ChallengeUtils
import kotlinx.coroutines.launch

class SelectedChallengeListFragment : ChallengeListFragment() {

    override fun getChallenges() {
        val args: SelectedChallengeListFragmentArgs by navArgs()
        val challenges = args.challenges
        var challengeList = challenges.toList()
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
           challengeList = ChallengeUtils().sortChallengesByDistance(challengeList, requireContext())
            initViewAdapter(challengeList)
            bindRecyclerView()
        }
    }

    override fun initViewAdapter(challenges: List<Challenge>) {
        viewAdapter = SelectedChallengeListAdapter(challenges,requireContext())
    }

}