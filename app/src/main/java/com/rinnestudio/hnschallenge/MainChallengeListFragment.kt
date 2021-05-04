package com.rinnestudio.hnschallenge

import androidx.fragment.app.viewModels

class MainChallengeListFragment : AbstractChallengeListFragment() {
    private val viewModel: MainChallengeListViewModel by viewModels()

    override fun initViewAdapter() {
        viewAdapter = MainChallengeListAdapter(challenges.toMutableList(), requireContext())
    }

    override fun getChallenges() {
        viewModel.getChallenges().observe(viewLifecycleOwner) {
            challenges = it.toMutableList()
            if (challenges.isEmpty()) {
                displayTextForEmptyList()
            } else {
                initViewAdapter()
                bindRecyclerView()
            }
        }
    }
}