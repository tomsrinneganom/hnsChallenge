package com.dedstudio.hnschallenge

import androidx.navigation.findNavController

class SelectedChallengeListAdapter(challenges: List<Challenge>) : ChallengeListAdapter(challenges) {
    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        holder.bind(challenges[position])
        holder.item.apply {
            isClickable = true
            setOnClickListener {
                val navDirections =
                    SelectedChallengeListFragmentDirections.actionSelectedChallengeListFragmentToMapChallengeExecutionFragment(
                        challenges[position]
                    )
                it.findNavController().navigate(navDirections)
            }
        }
    }
}