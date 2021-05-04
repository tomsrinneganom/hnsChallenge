package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.HeatmapLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource

abstract class AbstractHeatmapMapFragment : AbstractMapFragment() {
    private val maxDistaceForChoosingChallenges: Double = 0.001
    private val minDistaceForChoosingChallenges: Double = -0.001

    protected var challenges: LiveData<List<Challenge>> = MutableLiveData()
    private val sourceID: String = "earthquakes"
    private val layerID = "earthquakes-heat"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initChallengeObserver()
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        super.onMapReady(mapboxMap)
        addEarthquakeSource()
        mapboxMap.addOnMapClickListener(onMapClickListener)
    }


    private val onMapClickListener = MapboxMap.OnMapClickListener { point ->
        if (challenges.value != null) {
            val selectedChallenges = computeSelectedChallenges(point, challenges.value!!)
            if (selectedChallenges.isNotEmpty()) {
                openSelectedChallengeList(selectedChallenges.toTypedArray())
            }
        }
        true
    }

    private fun addEarthquakeSource() {
        challenges.observe(this) {

            val coordinates = getListOfPointFromChallenges()
          Log.i("Log_tag",  "coordinates.size ${coordinates.size}")
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

    private fun computeSelectedChallenges(point: LatLng, challenges: List<Challenge>): List<Challenge> {
        val selectedChallenges = mutableListOf<Challenge>()
        challenges.forEach { challenge ->
            val differenceLatitude = point.latitude - challenge.latitude!!
            if (differenceLatitude < maxDistaceForChoosingChallenges && differenceLatitude > minDistaceForChoosingChallenges) {

                val differenceLongitude = point.longitude - challenge.longitude!!
                if (differenceLongitude < maxDistaceForChoosingChallenges && differenceLongitude > minDistaceForChoosingChallenges) {
                    selectedChallenges.add(challenge)
                }
            }
        }
        return selectedChallenges
    }

    private fun getListOfPointFromChallenges(): ArrayList<Point> {
        val coordinates = ArrayList<Point>()

        challenges.value!!.forEach { challenge ->
            if (challenge.latitude != null && challenge.longitude != null) {
                coordinates.add(Point.fromLngLat(challenge.longitude!!, challenge.latitude!!))
            }
            coordinates.add(Point.fromLngLat(180.0, 90.0))
            coordinates.add(Point.fromLngLat(180.0, 90.0))

        }

        return coordinates

    }

    protected abstract fun initChallengeObserver()
    protected abstract fun openSelectedChallengeList(selectedChallenges: Array<Challenge>)


}