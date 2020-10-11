package com.dedstudio.hnschallenge

import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.geojson.*
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.main_map_fragment.view.*
import kotlinx.android.synthetic.main.map_challenge_execution_fragment.view.*

class MapChallengeExecutionFragment : MapFragment() {

    private lateinit var challenge: Challenge
    private lateinit var floatingActionButtonCamera: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.map_challenge_execution_fragment, container, false)
        val args: MapChallengeExecutionFragmentArgs by navArgs()
        challenge = args.challenge
        imageMoveToCurrentLocation = view.floatingActionButtonChallengeExecutionLocation
        floatingActionButtonCamera = view.floatingActionButtonChallengeExecutionCamera
        floatingActionButtonCamera.setOnClickListener {
          val navDirections = MapChallengeExecutionFragmentDirections.actionMapChallengeExecutionFragmentToCameraChallengeExecutionFragment(challenge)
            findNavController().navigate(navDirections)
        }
        mapView = view.findViewById(R.id.mapViewChallengeExecution)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
                this.mapboxMap = mapboxMap
                addCircleLayer(style)
                addShowingLocation(style)
            }
        }
        return view
    }

    private fun addCircleLayer(loadedMapStyle: Style) {
        val feature =
            Feature.fromGeometry(Point.fromLngLat(challenge.longitude!!, challenge.latitude!!))
        loadedMapStyle.addSource(GeoJsonSource("circle-source", feature))
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
        loadedMapStyle.addLayer(layer)
    }
}