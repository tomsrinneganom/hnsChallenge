package com.rinnestudio.hnschallenge.repository

import android.graphics.Bitmap
import android.icu.util.Calendar
import android.location.Location
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.Challenge
import com.rinnestudio.hnschallenge.profile.Profile
import com.rinnestudio.hnschallenge.repository.firebaseRepository.ChallengeFirebaseRepository
import java.io.File

class ChallengeRepository {
    private val firebaseRepository = ChallengeFirebaseRepository()
    fun getChallengeById(id: String) {

    }

    suspend fun getChallengesByCreatorId(creatorId: String) =
        firebaseRepository.getChallengesByCreatorId(creatorId)

    private suspend fun addChallengeToDatabase(challenge: Challenge, challengePhoto: ByteArray) =
        firebaseRepository.addChallengeToDatabase(challenge, challengePhoto)

    suspend fun getOwnChallengeList() = ChallengeFirebaseRepository().getOwnChallengeList()

    suspend fun uploadChallengePhoto(creatorId: String, challengeId: String): Bitmap {
        val file = File.createTempFile("images", "jpg")

        return ChallengeFirebaseRepository().uploadChallengePhoto(creatorId, challengeId, file)
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
    ) =  firebaseRepository.deleteChallengeById(challengeId, creatorId)

    private fun getChallengeId(location: Location) =
        "${location.longitude + location.latitude}${Calendar.getInstance().time}"


}