package com.rinnestudio.hnschallenge.repository

import android.graphics.Bitmap
import android.icu.util.Calendar
import android.location.Location
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.Challenge
import com.rinnestudio.hnschallenge.Profile
import com.rinnestudio.hnschallenge.repository.firebaseRepository.ChallengeFirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ChallengeRepository {
    private val firebaseRepository = ChallengeFirebaseRepository()

    suspend fun getChallengesByCreatorId(creatorId: String) =
        firebaseRepository.getChallengesByCreatorId(creatorId)

    private suspend fun addChallengeToDatabase(challenge: Challenge, challengePhoto: ByteArray) =
        firebaseRepository.addChallengeToDatabase(challenge, challengePhoto)

    suspend fun getSubscriptionsChallengeList(): List<Challenge> {
        val subscription = ProfileRepository().getSubscriptionList()

        if (!subscription.isNullOrEmpty()) {
            return ChallengeFirebaseRepository().getSubscriptionsChallengeList(subscription)
        }

        return emptyList()
    }

    suspend fun uploadChallengePhoto(creatorId: String, challengeId: String): Bitmap {
        return withContext(Dispatchers.IO) {
            val file = File.createTempFile("images", "jpg")
            return@withContext ChallengeFirebaseRepository().uploadChallengePhoto(creatorId,
                challengeId,
                file)
        }
    }

    suspend fun createChallenge(
        profile: Profile,
        location: Location,
        challengePhoto: ByteArray,
    ): Boolean {
        val challengeId = getChallengeId(location)
        val challenge = Challenge(
            challengeId,
            location.latitude,
            location.longitude,
            location.accuracy,
            creatorName = profile.userName,
            creatorProfilePhotoReference = profile.photoReference,
            creatorId = profile.id
        )
        return addChallengeToDatabase(challenge, challengePhoto)
    }

    suspend fun deleteChallenge(
        challengeId: String,
        creatorId: String = Firebase.auth.currentUser!!.uid,
    ) = firebaseRepository.deleteChallengeById(challengeId, creatorId)



    private fun getChallengeId(location: Location) =
        "${location.longitude + location.latitude}${Calendar.getInstance().time}"


}