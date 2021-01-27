package com.rinnestudio.hnschallenge.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.tasks.await

class LocationUtils {
    //TODO()
    suspend fun getLastKnowLocation(context: Context): Location? {
        Log.i("Log_tag", "getLastKnowLocation()")
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("Log_tag", "error premission")
        }
        Log.i("Log_tag", "start")
        val lastLocation = fusedLocationClient.lastLocation.await()
        if (lastLocation.latitude != null) {
            Log.i("Log_tag", "${lastLocation.latitude} ${lastLocation.longitude}")
        } else {
            Log.i("Log_tag", "error")
        }
        Log.i("Log_tag", "end")
        return lastLocation
    }
}