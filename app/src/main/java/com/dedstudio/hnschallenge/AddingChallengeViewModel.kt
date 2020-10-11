package com.dedstudio.hnschallenge

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class AddingChallengeViewModel(application: Application) : AndroidViewModel(application) {

    fun addingChallenge(pathToImageFile: String) {
        val image = ImageUtils().convertFileImageToByteArray(pathToImageFile)

        val location = LocationUtils().getLastKnowLocation(getApplication())
        location.observeForever {
            addingChallengeToDatabase(it, image)
        }
    }

    private fun addingChallengeToDatabase(
        location: Location,
        image: ByteArray
    ) {
        val currentUser = Firebase.auth.currentUser

        val challengeId =
            "${location.longitude + location.latitude}${Calendar.getInstance().time }"
        val reference =
            Firebase.storage.reference.child("${currentUser!!.uid}/$challengeId.jpg")
        val task = reference.putBytes(image)

        task.addOnSuccessListener {
            Log.i("Log_tag", "SUCCESSS add image challenge")

        }.addOnFailureListener { exception ->
            Log.i("Log_tag", "exception add image challenge ${exception.message}")
        }


        val firestore = Firebase.firestore
        val challenge = Challenge(
            challengeId,
            location.latitude,
            location.longitude,
            creatorName = currentUser.displayName,
            creatorPhoto = currentUser.photoUrl.toString(),
            creatorId = currentUser.uid
        )
        firestore.collection("challenges").document("users").collection(currentUser.uid)
            .document(challengeId).set(challenge)
            .addOnSuccessListener {
                Log.i("Log_tag", "SUCCESSS add challenge info")
            }.addOnFailureListener { exception ->
                Log.i("Log_tag", "${exception.message}")
            }
    }


}