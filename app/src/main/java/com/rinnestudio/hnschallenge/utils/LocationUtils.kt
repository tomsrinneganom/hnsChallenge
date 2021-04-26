package com.rinnestudio.hnschallenge.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.android.gms.location.*
import com.rinnestudio.hnschallenge.PermissionManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationUtils() {

    fun getUserLocation(context: Context): LiveData<Location?> =
        liveData {
            if (PermissionManager().checkAccessToLocation(context)) {
                val fusedLocationProviderClient: FusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                emit(getLastKnowLocation(fusedLocationProviderClient) ?: requestLocationUpdate(
                    fusedLocationProviderClient))
            } else {
                emit(null)
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

            locationProviderClient.requestLocationUpdates(LocationRequest.create(),
                locationCallback,
                Looper.getMainLooper()).addOnFailureListener { exception ->
                Log.i("Log_tag", "requestLocationUpdate() Exception: ${exception.message}")
                it.resume(null)
            }
        }
}

