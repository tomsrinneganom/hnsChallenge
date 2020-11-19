package com.dedstudio.hnschallenge

import android.app.Application
import android.graphics.Bitmap
import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.dedstudio.hnschallenge.repository.ChallengeRepository
import com.dedstudio.hnschallenge.repository.ProfileRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class CreateChallengeViewModel(application: Application) : AndroidViewModel(application) {

    fun addingChallenge(photo: Bitmap) {

        val image = ImageUtils().convertBitmapToByteArray(photo)
        val repository = ChallengeRepository()
        val location = LocationUtils().getLastKnowLocation(getApplication())
        val currentUser = ProfileRepository().getProfile(Firebase.auth.uid!!)

        location.observeForever { location ->
            val challengeId =
                "${location.longitude + location.latitude}${Calendar.getInstance().time}"

            currentUser.observeForever { profile ->
                val challenge = Challenge(
                    challengeId,
                    location.latitude,
                    location.longitude,
                    creatorName = profile.userName,
                    creatorPhoto = profile.photoReference,
                    creatorId = profile.id
                )
                repository.addChallenge(challenge, image)
            }
        }

    }
}