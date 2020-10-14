package com.dedstudio.hnschallenge

import android.util.Log
import androidx.navigation.fragment.navArgs

class SelectedChallengeListFragment : ChallengeListFragment() {

    override fun getChallenges() {
        val args: SelectedChallengeListFragmentArgs by navArgs()
        val challenges = args.challenges
        Log.i("Log_tag", challenges.size.toString())
        val challengeList = challenges.toList()
        initViewAdapter(challengeList)
        bindRecyclerView()
    }

    override fun initViewAdapter(challenges: List<Challenge>) {
        viewAdapter = SelectedChallengeListAdapter(challenges)
    }

}