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

    override suspend fun getChallenges() {
        challenges = viewModel.getChallenges()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_map_fragment, container, false)
        mapView = view.findViewById(R.id.mainMapMapView)
        fabLocation = view.findViewById(R.id.mainMapFabLocation)
        return view
    }


    override fun openSelectedChallengeList(selectedChallenges: Array<Challenge>) {
        val navDirections =
            MainMapFragmentDirections.actionMainMapNavigationItemToSelectedChallengeListFragment(
                selectedChallenges
            )
        findNavController().navigate(navDirections)
    }


}