package com.rinnestudio.hnschallenge

import android.graphics.Color.parseColor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mapbox.geojson.*
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.rinnestudio.hnschallenge.utils.ChallengeUtils
import kotlinx.coroutines.launch

class MapChallengeExecutionFragment : AbstractMapFragment() {

    private lateinit var challenge: Challenge
    private val viewModel: MapChallengeExecutionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.map_challenge_execution_fragment, container, false)
        val args: MapChallengeExecutionFragmentArgs by navArgs()
        challenge = args.challenge

        fabLocation =
            view.findViewById(R.id.floatingActionButtonChallengeExecutionLocation)
        mapView = view.findViewById(R.id.mapViewChallengeExecution)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonChallengeExecutionCamera)
            .setOnClickListener {
                val navDirections =
                    MapChallengeExecutionFragmentDirections.actionMapChallengeExecutionFragmentToCameraChallengeExecutionFragment(
                        challenge)
                findNavController().navigate(navDirections)
            }
        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonChallengeExecutionImage)
            .setOnClickListener {
                val navDirections =
                    MapChallengeExecutionFragmentDirections.actionMapChallengeExecutionFragmentToDisplayImageFragment(
                        ChallengeUtils().generateChallengePhotoReference(challenge.creatorId!!,
                            challenge.id!!).path)
                findNavController().navigate(navDirections)
            }
        if (Firebase.auth.currentUser != null && challenge.creatorId == Firebase.auth.currentUser!!.uid) {
            Log.i("Log_tag", "true")
            view.findViewById<FloatingActionButton>(R.id.floatingActionButtonDeleteChallenge)
                .apply {
                    visibility = View.VISIBLE
                    setOnClickListener { deleteChallenge() }
                }
        }
        return view
    }

    private fun deleteChallenge() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            val result = viewModel.deleteChallenge(challenge.id!!, challenge.creatorId!!)
            if (result) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        super.onMapReady(mapboxMap)
        addCircleLayer()

    }

    private fun addCircleLayer() {
        mapboxMap.getStyle { style ->
            val feature =
                Feature.fromGeometry(Point.fromLngLat(challenge.longitude!!, challenge.latitude!!))
            style.addSource(GeoJsonSource("circle-source", feature))
            val layer = CircleLayer("circle-layer", "circle-source").withProperties(
                circleRadius(
                    interpolate(
                        linear(), zoom(),
                        stop(2, 5f),
                        stop(3, 20f)
                    )
                ),
                circleColor(parseColor("#B323FF"))
            );
            style.addLayer(layer)
        }
    }
}