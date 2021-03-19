package com.rinnestudio.hnschallenge.utils

import android.content.Context
import android.location.Location
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rinnestudio.hnschallenge.Challenge

class ChallengeUtils {
    fun generateChallengePhotoReference(creatorId: String, id: String) =
        Firebase.storage.reference.child("${creatorId}/${id}.jpg")

    suspend fun sortChallengesByDistance(
        challenges: List<Challenge>,
        context: Context,
    ): List<Challenge> {
        val currentLocation =
            LocationUtils().getUserLocation(context)
        return if (currentLocation != null)
            challenges.sortedBy {
                val challengeLocation = Location("challengeLocation").apply {
                    latitude = it.latitude!!
                    longitude = it.longitude!!
                }
                it.distanceToCurrentLocation = currentLocation.distanceTo(challengeLocation)
                it.distanceToCurrentLocation
            }
        else
            challenges
    }
}