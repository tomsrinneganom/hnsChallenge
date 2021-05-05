package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
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
        fabChallengeList = view.findViewById(R.id.profileMapFabChallengeList)
        fabChallengeList.setOnClickListener { openChallengeList() }
        return view
    }

    protected fun navigate(navDirections: NavDirections){
        crossFadeView.visibility = View.VISIBLE
        mapView.visibility = View.INVISIBLE
        findNavController().navigate(navDirections)
    }

    protected fun displayTostForEmptyChallengeList(){
        Toast.makeText(requireContext(),resources.getString(R.string.listIsEmpty),Toast.LENGTH_LONG).show()
    }

    abstract fun openChallengeList()
}