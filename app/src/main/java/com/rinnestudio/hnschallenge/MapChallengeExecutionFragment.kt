package com.rinnestudio.hnschallenge

import android.graphics.Color.parseColor
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeta
import com.mapbox.turf.TurfTransformation
import com.rinnestudio.hnschallenge.utils.ChallengeUtils


class MapChallengeExecutionFragment : AbstractMapFragment() {

    private lateinit var challenge: Challenge
    private val viewModel: MapChallengeExecutionViewModel by viewModels()
    private val viewModelChallengeList :ChallengeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.map_challenge_execution_fragment, container, false)
        val args: MapChallengeExecutionFragmentArgs by navArgs()
        challenge = args.challenge

        setUpFabs(view)

        return view
    }

    private fun setUpFabs(view: View) {
        view.findViewById<FloatingActionButton>(R.id.cameraFab)
            .setOnClickListener {
                val navDirections =
                    MapChallengeExecutionFragmentDirections.actionMapChallengeExecutionFragmentToCameraChallengeExecutionFragment(
                        challenge)
                findNavController().navigate(navDirections)
            }

        view.findViewById<FloatingActionButton>(R.id.challengePhotoFab)
            .setOnClickListener {
                val navDirections =
                    MapChallengeExecutionFragmentDirections.actionMapChallengeExecutionFragmentToDisplayImageFragment(
                        ChallengeUtils().generateChallengePhotoReference(challenge.creatorId!!,
                            challenge.id!!).path)
                findNavController().navigate(navDirections)
            }

        if (Firebase.auth.currentUser != null && challenge.creatorId == Firebase.auth.currentUser!!.uid) {
            view.findViewById<FloatingActionButton>(R.id.deleteChallengeFab)
                .apply {
                    visibility = View.VISIBLE
                    setOnClickListener { deleteChallenge() }
                }
        }
    }

    private fun deleteChallenge() {
        viewModel.deleteChallenge(challenge.id!!, challenge.creatorId!!)
            .observe(viewLifecycleOwner) {
                if (it) {
                    viewModelChallengeList.deletedChallenge.add(challenge)
                    Log.i("Log_tag","size deleted challenges ${viewModelChallengeList.deletedChallenge.size }")
                    findNavController().navigateUp()
                }
            }

    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        super.onMapReady(mapboxMap)
        addCircleLayer()

    }

    private fun addCircleLayer() {
        mapboxMap.getStyle {
            val centerPointOfCircle = generateCeneterPointOfCircle()

            val f = TurfTransformation.circle(Point.fromLngLat(centerPointOfCircle.longitude,
                centerPointOfCircle.latitude), 200.0, 360, TurfConstants.UNIT_METERS);
            val feature =
                Feature.fromGeometry(Point.fromLngLat(centerPointOfCircle.longitude,
                    centerPointOfCircle.latitude))
            val source = GeoJsonSource("circle-source", feature)
            source.setGeoJson(Polygon.fromOuterInner(LineString.fromLngLats(TurfMeta.coordAll(f,
                false))))
            it.addSource(source)
            val polygonFillLayer = FillLayer("layer-id", "circle-source")
                .withProperties(fillColor(parseColor("#b134eb")), fillOpacity(0.5f))
            it.addLayer(polygonFillLayer)
        }
    }

    private fun generateCeneterPointOfCircle(): LatLng {
        val earth = 6371000.0
        val x = (-100..100).random()
        val y = (-100..100).random()
        val lat: Double = challenge.latitude!! + (x / earth) * (180 / Math.PI)
        val lng: Double =
            challenge.longitude!! + (y / earth) * (180 / Math.PI) / kotlin.math.cos(challenge.latitude!! * Math.PI / 180)
        val l1 = Location("test1").apply {
            latitude = lat
            longitude = lng
        }
        val l2 = Location("test2").apply {
            latitude = challenge.latitude!!
            longitude = challenge.longitude!!
        }
        Log.i("Log_tag", "distance: ${l1.distanceTo(l2)}")

        return LatLng(lat, lng)
    }
}