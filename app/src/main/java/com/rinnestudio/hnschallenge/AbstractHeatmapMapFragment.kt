package com.rinnestudio.hnschallenge

import android.util.Log
import androidx.lifecycle.coroutineScope
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.HeatmapLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.coroutines.launch

abstract class AbstractHeatmapMapFragment : AbstractMapFragment() {
    private val sourceID: String = "earthquakes"
    private val layerID = "earthquakes-heat"

    override fun onMapReady(mapboxMap: MapboxMap) {
        super.onMapReady(mapboxMap)
        addHeatmap()
        mapboxMap.addOnMapClickListener(onMapClickListener)
    }

    private fun addHeatmap() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            getChallenges()
            addEarthquakeSource()
        }
    }

    protected abstract suspend fun getChallenges()

    private val onMapClickListener = MapboxMap.OnMapClickListener { point ->
        val selectedChallenges = mutableListOf<Challenge>()
        //TODO() Добавить проверки на null
        //если пусто выдавать  сообщение что челлендж был удалён
        challenges.forEach { challenge ->
            val differenceLatitude = point.latitude - challenge.latitude!!
            if (differenceLatitude < 0.001 && differenceLatitude > -0.001) {
                val differenceLongitude = point.longitude - challenge.longitude!!
                if (differenceLongitude < 0.001 && differenceLongitude > -0.001) {
                    selectedChallenges.add(challenge)
                }
            }
        }
        Log.i("Log_tag", "${selectedChallenges.size}")
        if (selectedChallenges.isNotEmpty()) {
            openSelectedChallengeList(selectedChallenges.toTypedArray())
        }
        true
    }

    protected abstract fun openSelectedChallengeList(selectedChallenges: Array<Challenge>)

    private fun addEarthquakeSource() {
        val coordinates = ArrayList<Point>()
        challenges.forEach { challenge ->
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
                    literal(0.2), rgba(179, 35, 255, 0.4),
                    literal(0.4), rgba(179, 35, 255, 0.5),
                    literal(0.6), rgba(179, 35, 255, 0.6),
                    literal(0.8), rgba(179, 35, 255, 0.7),
                    literal(1), rgba(179, 35, 255, 0.7)
                )
            ),
            heatmapIntensity(interpolate(
                linear(), zoom(),
                literal(0), literal(0),
                literal(22), literal(1)
            )),
            heatmapRadius(interpolate(
                linear(), zoom(),
                literal(0), literal(10),
                literal(7), literal(50),
                literal(13), literal(70),
                literal(22), literal(500)
            )),
            heatmapWeight(
                interpolate(
                    linear(), get("mag"),
                    stop(0, 0.5),
                    stop(20, 0.7)
                )
            )
        )
        if (style.getLayer(layerID) == null) {
            style.addLayer(layer)
        }
    }

}