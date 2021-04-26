package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainMapFragment : AbstractHeatmapMapFragment() {

    private val viewModel: MainMapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.main_map_fragment, container, false)
    }

    override fun initChallengeObserver() {
        challenges = viewModel.getChallenges()
    }

    override fun openSelectedChallengeList(selectedChallenges: Array<Challenge>) {
        val navDirections =
            MainMapFragmentDirections.actionMainMapNavigationItemToSelectedChallengeListFragment(
                selectedChallenges
            )
        findNavController().navigate(navDirections)
    }

}