package com.dedstudio.hnschallenge

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

class SelectedChallengeListFragment : ChallengeListFragment() {

    override fun getChallenges() {
        val args: SelectedChallengeListFragmentArgs by navArgs()
        val challenges = args.challenges
        Log.i("Log_tag", challenges.size.toString())
        val challengeList = challenges.toList()
        initViewAdapter(challengeList)
        bindRecyclerView(challengeList)
    }

    override fun initViewAdapter(challenges: List<Challenge>) {
        viewAdapter = SelectedChallengeListAdapter(challenges)
    }

}