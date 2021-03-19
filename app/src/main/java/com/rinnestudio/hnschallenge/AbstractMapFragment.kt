package com.rinnestudio.hnschallenge

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin
import com.rinnestudio.hnschallenge.utils.LocationUtils
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine


abstract class AbstractMapFragment : Fragment(), OnMapReadyCallback {
    protected lateinit var mapView: MapView
    protected lateinit var mapboxMap: MapboxMap
    protected lateinit var challenges: List<Challenge>
    protected lateinit var fabLocation: FloatingActionButton
    protected lateinit var testView: View
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fabLocation.apply {
            isClickable = true
            setOnClickListener {
                moveToCurrentLocation()
            }
        }
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap

        mapboxMap.setStyle(Style.Builder()
            .fromUri("mapbox://styles/hnschallenge/ckl10wjky00x117s68s2ux3eu")) {
            initMap(it)
        }
    }

    private fun initMap(style: Style) {
//        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            mapLocalization(style)
            runBlocking {
                moveToCurrentLocation()
            }
            disableCompass()
            addShowingLocation(style)
            crossfade()
//        }
    }

    protected fun mapLocalization(style: Style) {
        Log.i("Log_tag", "mapLocalization()")
        val localizationPlugin = LocalizationPlugin(mapView, mapboxMap, style)
        localizationPlugin.matchMapLanguageWithDeviceDefault()
    }

    protected fun addShowingLocation(loadedMapStyle: Style) {
        Log.i("Log_tag", "addShowingLocation()")
        val locationComponent = mapboxMap.locationComponent
        locationComponent.activateLocationComponent(
            LocationComponentActivationOptions.builder(requireContext(), loadedMapStyle).build()
        )
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        Log.i("Log_tag", "Last know location == null2")
        locationComponent.isLocationComponentEnabled = true
        locationComponent.cameraMode = CameraMode.TRACKING
        locationComponent.renderMode = RenderMode.COMPASS


    }

    private fun moveToCurrentLocation() = viewLifecycleOwner.lifecycle.coroutineScope.launch {
        Log.i("Log_tag", "moveToCurrentLocation()")
        val location = LocationUtils().getUserLocation(requireContext())
        if (location != null) {
            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(location), 13.5))
        }
        Log.i("Log_tag", "end moveToCurrentLocation()")

    }

    private fun crossfade() {
        Log.i("Log_tag", "crossfade()")
        testView.apply {
            alpha = 1f
            animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(null)
        }
    }

    private fun disableCompass() {
        Log.i("Log_tag", "disableCompass()")

        mapboxMap.uiSettings.apply {
            isCompassEnabled = false
            isRotateGesturesEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

}