package com.rinnestudio.hnschallenge.utils

import android.location.Location
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rinnestudio.hnschallenge.Challenge

class ChallengeUtils {
    fun generateChallengePhotoReference(creatorId: String, id: String) =
        Firebase.storage.reference.child("${creatorId}/${id}.jpg")
}