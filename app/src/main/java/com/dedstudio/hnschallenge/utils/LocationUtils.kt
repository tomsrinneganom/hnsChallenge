package com.dedstudio.hnschallenge.utils

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
import com.google.android.gms.dynamic.DeferredLifecycleHelper
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.tasks.Tasks.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.asDeferred
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