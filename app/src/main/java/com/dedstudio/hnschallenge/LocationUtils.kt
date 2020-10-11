package com.dedstudio.hnschallenge

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.coroutineScope
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat

class LocationUtils {
    fun getLastKnowLocation(context: Context): LiveData<Location> {
        val location = MutableLiveData<Location>()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
        }
            fusedLocationClient.lastLocation.addOnCompleteListener {
                val i = Log.i("Log_tag", "complete: ${it.result?.latitude} ")
                 location.value = it.result
            }.addOnFailureListener {
                Log.i("Log_tag", "exception: ${it.message}")
            }.addOnSuccessListener {
                Log.i("Log_tag", "success")
            }
        return location
    }
}