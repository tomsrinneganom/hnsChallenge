package com.rinnestudio.hnschallenge

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat

class PermissionManager {

    fun requestLocationPermission(resultLauncher: ActivityResultLauncher<String>) {
        resultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun checkAccessToLocation(context: Context): Boolean {
        return (ActivityCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    fun requestCameraPermission(resultLauncher: ActivityResultLauncher<String>) {
        resultLauncher.launch(Manifest.permission.CAMERA)
    }

    fun checkAccessToCamera(context: Context): Boolean {
        return (ActivityCompat.checkSelfPermission(context,
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }

}