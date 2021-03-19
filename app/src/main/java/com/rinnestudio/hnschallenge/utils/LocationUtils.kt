package com.rinnestudio.hnschallenge.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationUtils() {

    suspend fun getUserLocation(context: Context): Location? {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        } else {
            val fusedLocationProviderClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            val lastKnowLocation = getLastKnowLocation(fusedLocationProviderClient)
            if (lastKnowLocation == null) {
                return requestLocationUpdate(fusedLocationProviderClient)
            }
            else{
               return lastKnowLocation
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastKnowLocation(locationProviderClient: FusedLocationProviderClient): Location? =
        suspendCoroutine {
            locationProviderClient.lastLocation.addOnCompleteListener { location ->
                it.resume(location.result)
            }.addOnFailureListener { exception ->
                Log.i("Log_tag", "getLastKnowLocation() Exception: ${exception.message}")
                it.resume(null)
            }
        }

    @SuppressLint("MissingPermission")
    private suspend fun requestLocationUpdate(locationProviderClient: FusedLocationProviderClient): Location? =
        suspendCoroutine {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(location: LocationResult) {
                    super.onLocationResult(location)
                    it.resume(location.lastLocation)
                }
            }

            locationProviderClient.requestLocationUpdates(LocationRequest(),
                locationCallback,
                Looper.getMainLooper()).addOnFailureListener { exception->
                Log.i("Log_tag", "requestLocationUpdate() Exception: ${exception.message}")
                it.resume(null)
            }
        }
}
