package com.dedstudio.hnschallenge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mapbox.geojson.*
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.HeatmapLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.heatmapWeight
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.main_map_fragment.view.*

open class MainMapFragment : MapFragment() {

    private val viewModel: MainMapViewModel by viewModels()
    private val sourceID: String = "earthquakes"
    private val layerID = "earthquakes-heat"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(Firebase.auth.currentUser == null){
            Log.i("Log_tag","null")
            findNavController().navigate(R.id.signInFragment)
        }
        Log.i("Log_tag","!= null")
        val view = inflater.inflate(R.layout.main_map_fragment, container, false)
        mapView = view.findViewById(R.id.main_map_view)
        imageMoveToCurrentLocation = view.floatingActionButtonLocation

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
                this.mapboxMap = mapboxMap
                mapLocalization(style)
                addHeatmap(style)
                addShowingLocation(style)
                this.mapboxMap.addOnMapClickListener(onMapClickListener)
            }
        }

    }

    protected fun addHeatmap(style: Style) {
        challenges = viewModel.getChallenges()
        challenges.observeForever {
            addEarthquakeSource(style)
        }
    }

    private val onMapClickListener = MapboxMap.OnMapClickListener { point ->
        val selectedChallenges = mutableListOf<Challenge>()
        //TODO() Добавить проверки на null
        //если пусто выдавать  сообщение что челлендж был удалён
        challenges.value?.forEach { challenge ->
            val differenceLatitude = point.latitude - challenge.latitude!!
            Log.i("Log_tag", "$differenceLatitude")
            if (differenceLatitude < 0.001 && differenceLatitude > -0.001) {
                val differenceLongitude = point.longitude - challenge.longitude!!
                Log.i("Log_tag", "$differenceLongitude")
                if (differenceLongitude < 0.001 && differenceLongitude > -0.001) {
                    selectedChallenges.add(challenge)
                }
            }
        }
        Log.i("Log_tag", "${selectedChallenges.size}")
        if (selectedChallenges.isNotEmpty()) {
            val navDirections =
                MainMapFragmentDirections.actionMainMapNavigationItemToSelectedChallengeListFragment(
                    selectedChallenges.toTypedArray()
                )
            findNavController().navigate(navDirections)
        }
        true
    }

    fun addEarthquakeSource(s: Style) {
        val coordinates = ArrayList<Point>()
        challenges.value?.forEach { challenge ->
            if (challenge.latitude != null && challenge.longitude != null) {
                coordinates.add(Point.fromLngLat(challenge.longitude!!, challenge.latitude!!))
            }
        }
        coordinates.add(Point.fromLngLat(180.0, 90.0))
        coordinates.add(Point.fromLngLat(180.0, 90.0))

        val lineString = LineString.fromLngLats(coordinates)
        val featureCollection = FeatureCollection.fromFeature(Feature.fromGeometry(lineString))
        mapboxMap.getStyle { style ->
            if (style.getSource(sourceID) == null) {
                val geoJsonSource = GeoJsonSource(sourceID, featureCollection)
                style.addSource(geoJsonSource)
            }
            addHeatmapLayer(style)
        }
    }

    private fun addHeatmapLayer(style: Style) {
        val layer = HeatmapLayer(layerID, sourceID)
        layer.sourceLayer = sourceID
        layer.setProperties(
            heatmapColor(
                interpolate(
                    linear(), heatmapDensity(),
                    literal(0), rgba(179, 35, 255, 0),
                    literal(0.2), rgba(179, 35, 255, 0.2),
                    literal(0.4), rgba(179, 35, 255, 0.3),
                    literal(0.6), rgba(179, 35, 255, 0.5),
                    literal(0.8), rgba(179, 35, 255, 0.6),
                    literal(1), rgba(179, 35, 255, 1)
                )
            ),
            heatmapWeight(
                interpolate(
                    linear(), get("mag"),
                    stop(0, 0),
                    stop(6, 1)
                )
            )
        )
        if (style.getLayer(layerID) == null) {
            style.addLayer(layer)
        }
    }

}