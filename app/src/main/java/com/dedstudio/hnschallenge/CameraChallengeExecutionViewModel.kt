package com.dedstudio.hnschallenge

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayInputStream
import java.io.File

class CameraChallengeExecutionViewModel : ViewModel() {
    fun loadImage(creatorID: String, challengeID: String): LiveData<Bitmap> {
        val reference = Firebase.storage.reference.child("${creatorID}/${challengeID}.jpg")
        Log.i("Log_tag","LoadImage")

        val file = File.createTempFile("images", "jpg")
        val bitmap = MutableLiveData<Bitmap>()
        reference.getFile(file).addOnSuccessListener {
            Log.i("Log_tag","LoadImage success=")
            bitmap.value =
                BitmapFactory.decodeFile(file.path)
        }.addOnFailureListener {
            Log.i("Log_tag","LoadImage failure")
        }
        return bitmap
    }
}