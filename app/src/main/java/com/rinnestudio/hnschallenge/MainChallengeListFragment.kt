package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels

class MainChallengeListFragment : AbstractChallengeListFragment() {
    private val viewModel: MainChallengeListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeTextForEmptyList(resources.getString(R.string.emptyMainChalllengeList))
    }
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