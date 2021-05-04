package com.rinnestudio.hnschallenge

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.rinnestudio.hnschallenge.utils.ChallengeUtils

class SelectedChallengeListFragment : AbstractChallengeListFragment() {
    val viewModel: ChallengeListViewModel by activityViewModels()

    override fun getChallenges() {
        val args: SelectedChallengeListFragmentArgs by navArgs()
        challenges = args.challenges.toMutableList()

        val deletedChallenge = viewModel.deletedChallenge
        challenges.removeAll(deletedChallenge)

        if (challenges.isEmpty()) {
            displayTextForEmptyList()
        } else {
            ChallengeUtils().sortChallengesByDistance(challenges, requireContext()).observe(this) {
                Log.i("Log_tag", "getChallenges live data")
                challenges = it.toMutableList()
                initViewAdapter()
                bindRecyclerView()
            }
        }
    }

    override fun initViewAdapter() {
        viewAdapter = SelectedChallengeListAdapter(challenges, requireContext())
    }

}