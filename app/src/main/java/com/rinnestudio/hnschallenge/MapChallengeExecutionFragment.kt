package com.rinnestudio.hnschallenge

import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.geojson.*
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource

class MapChallengeExecutionFragment : MapFragment() {

    private lateinit var challenge: Challenge

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.map_challenge_execution_fragment, container, false)
        val args: MapChallengeExecutionFragmentArgs by navArgs()
        challenge = args.challenge
        imageMoveToCurrentLocation = view.findViewById(R.id.floatingActionButtonChallengeExecutionLocation)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonChallengeExecutionCamera).setOnClickListener {
            val navDirections =
                MapChallengeExecutionFragmentDirections.actionMapChallengeExecutionFragmentToCameraChallengeExecutionFragment(
                    challenge)
            findNavController().navigate(navDirections)
        }
        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonChallengeExecutionImage).setOnClickListener {

        }
        mapView = view.findViewById(R.id.mapViewChallengeExecution)
        return view
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