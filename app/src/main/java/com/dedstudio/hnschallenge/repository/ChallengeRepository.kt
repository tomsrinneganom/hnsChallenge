package com.dedstudio.hnschallenge.repository

import android.graphics.Bitmap
import android.icu.util.Calendar
import android.location.Location
import com.dedstudio.hnschallenge.Challenge
import com.dedstudio.hnschallenge.Profile
import com.dedstudio.hnschallenge.repository.firebaseRepository.ChallengeFirebaseRepository
import java.io.File

class ChallengeRepository {
    private val firebaseRepository = ChallengeFirebaseRepository()
    fun getChallengeById() {

    }

    fun getChallengesByProfileId() {

    }

    private suspend fun addChallengeToDatabase(challenge: Challenge, challengePhoto: ByteArray) =
        firebaseRepository.addChallengeToDatabase(challenge, challengePhoto)

    suspend fun getOwnChallengeList() = ChallengeFirebaseRepository().getOwnChallengeList()

    fun deleteChallengeById() {

    }

    suspend fun uploadChallengePhoto(creatorId: String, challengeId: String): Bitmap {
        val file = File.createTempFile("images", "jpg")

        return ChallengeFirebaseRepository().uploadChallengePhoto(creatorId, challengeId, file)
    }

    suspend fun createChallenge(profile: Profile, location: Location, challengePhoto: ByteArray): Boolean {
        val challengeId = getChallengeId(location)
        val challenge = Challenge(
            challengeId,
            location.latitude,
            location.longitude,
            creatorName = profile.userName,
            creatorProfilePhotoReference = profile.photoReference,
            creatorId = profile.id
        )
        return addChallengeToDatabase(challenge,challengePhoto)
    }

    private fun getChallengeId(location: Location) =
        "${location.longitude + location.latitude}${Calendar.getInstance().time}"

}