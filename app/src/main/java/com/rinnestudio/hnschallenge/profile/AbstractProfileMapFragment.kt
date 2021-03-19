package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rinnestudio.hnschallenge.AbstractHeatmapMapFragment
import com.rinnestudio.hnschallenge.R

abstract class AbstractProfileMapFragment : AbstractHeatmapMapFragment() {
    private lateinit var fabChallengeList: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.profile_map_fragment, container, false)
        mapView = view.findViewById(R.id.profileMapMapView)
        fabLocation = view.findViewById(R.id.profileMapFabLocation)
        fabChallengeList = view.findViewById(R.id.profileMapFabChallengeList)
        testView = view.findViewById(R.id.view3)
        fabChallengeList.setOnClickListener { openChallengeList() }
        return view
    }
    abstract fun openChallengeList()
    protected fun hideMapView(){
        mapView.visibility = View.INVISIBLE
    }
}