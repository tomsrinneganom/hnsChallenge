package com.rinnestudio.hnschallenge.utils

import android.content.Context
import android.location.Location
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rinnestudio.hnschallenge.Challenge
import kotlinx.coroutines.flow.collect

class ChallengeUtils {

    fun sortChallengesByDistance(
        challenges: List<Challenge>,
        context: Context,
    ) = liveData {

        LocationUtils().getUserLocation(context).asFlow().collect { location ->
            if (challenges.size == 1 && location != null) {

                challenges[0].distanceToCurrentLocation =
                    location.distanceTo(getChallengeLocation(challenges[0]))

                emit(challenges)

            } else if (location != null) {

                emit(challenges.sortedBy {
                    val challengeLocation = getChallengeLocation(it)
                    it.distanceToCurrentLocation = location.distanceTo(challengeLocation)
                    it.distanceToCurrentLocation
                })
            } else {
                emit(challenges)
            }
        }

    }


    private fun getChallengeLocation(challenge: Challenge): Location {
        return Location("challengeLocation").apply {
            latitude = challenge.latitude!!
            longitude = challenge.longitude!!
        }
    }

    fun generateChallengePhotoReference(creatorId: String, id: String) =
        Firebase.storage.reference.child("${creatorId}/${id}.jpg")




}