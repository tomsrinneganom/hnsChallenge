package com.rinnestudio.hnschallenge

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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


abstract class AbstractMapFragment : Fragment(), OnMapReadyCallback {

    protected lateinit var mapView: MapView
    protected lateinit var mapboxMap: MapboxMap
    private lateinit var locationFab: FloatingActionButton
    protected lateinit var crossFadeView: View
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private val permissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGaranted ->
            if (isGaranted) {
                mapboxMap.getStyle {
                    addShowingLocation(it)
                    initMapCamera()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = requireView().findViewById(R.id.mapView)
        locationFab = requireView().findViewById(R.id.locationFab)
        crossFadeView = requireView().findViewById(R.id.mapFadeView)
        bindLocationFab()
        bindBackPressedCallback()

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    private fun bindLocationFab() {
        locationFab.apply {
            isClickable = true
            setOnClickListener {
                moveToCurrentLocation()
            }
        }
    }


    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(Style.Builder()
            .fromUri(resources.getString(R.string.mapbox_style_uri))) {

            PermissionManager().requestLocationPermission(permissionResultLauncher)
            mapLocalization(it)
            disableCompass()
            crossfade()

        }
    }

    private fun mapLocalization(style: Style) {
        val localizationPlugin = LocalizationPlugin(mapView, mapboxMap, style)
        localizationPlugin.matchMapLanguageWithDeviceDefault()
    }

    @SuppressLint("MissingPermission")
    private fun addShowingLocation(loadedMapStyle: Style) {
        if (PermissionManager().checkAccessToLocation(requireContext())) {
            val locationComponent = mapboxMap.locationComponent
            locationComponent.activateLocationComponent(
                LocationComponentActivationOptions.builder(requireContext(), loadedMapStyle).build()
            )
            locationComponent.isLocationComponentEnabled = true
            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.COMPASS
        }
    }

    private fun bindBackPressedCallback() {
        val navController = findNavController()
        if (navController.currentDestination!!.id != R.id.mainMapNavigationItem) {
            onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback {
                crossFadeView.alpha = 1f
                this.isEnabled = false
                this.remove()
                findNavController().navigateUp()
            }
        }
    }

    private fun initMapCamera() {
        LocationUtils().getUserLocation(requireContext()).observe(this) { location ->
            if (location != null) {
                mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(location), 13.5))
            }
        }
    }

    private fun crossfade() {
        crossFadeView.apply {
            alpha = 1f
            animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(null)
//            visibility = View.GONE
        }
    }

    private fun moveToCurrentLocation() {
        LocationUtils().getUserLocation(requireContext()).observe(viewLifecycleOwner) { location ->
            if (location != null) {
                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(location), 16.5), 1000)
            }
        }
    }

    private fun disableCompass() {
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
//        TODO()
//        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
        try {
            if (onBackPressedCallback.isEnabled) {
                onBackPressedCallback.isEnabled = false
                onBackPressedCallback.remove()
            }
        } catch (exception: Exception) {
            Log.i("Log_tag", "exception")
        }
    }

}