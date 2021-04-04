package com.rinnestudio.hnschallenge.repository.firebaseRepository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.Challenge
import com.rinnestudio.hnschallenge.utils.ChallengeUtils
import kotlinx.coroutines.tasks.await
import java.io.File

class ChallengeFirebaseRepository {
    private val firestore = Firebase.firestore.collection("challenges")

    suspend fun getChallengesByCreatorId(creatorId: String): List<Challenge> {
        return firestore.whereEqualTo("creatorId", creatorId).get().continueWith {
            if (it.isComplete && it.isSuccessful) {
                it.result.toObjects<Challenge>()
            } else {
                listOf()
            }
        }.await()
    }

    suspend fun addChallengeToDatabase(challenge: Challenge, challengePhoto: ByteArray): Boolean {
        if (challenge.creatorId == null || challenge.id == null) {
            return false
        }
        val isUploadChallngePhoto =
            uploadChallengePhotoToDatabase(challenge.creatorId!!, challenge.id!!, challengePhoto)
        val isUploadChallenge =
            firestore.document(challenge.id!!).set(challenge)
                .continueWith {
                    it.isSuccessful
                }.await()

        return isUploadChallngePhoto && isUploadChallenge
    }


    suspend fun getSubscriptionsChallengeList(subscriptionList: MutableList<String>): List<Challenge>? {
        return firestore.whereIn("creatorId", subscriptionList).get().continueWith {
            if (it.isComplete && it.isSuccessful) {
                val f = it.result.toObjects<Challenge>()
                if (f == null) {
                    listOf()
                } else
                    f
            } else {
                listOf()
            }
        }.await()
    }

    private suspend fun uploadChallengePhotoToDatabase(
        creatorId: String,
        challengeId: String,
        challengePhoto: ByteArray,
    ): Boolean {
        val reference =
            ChallengeUtils().generateChallengePhotoReference(creatorId, challengeId)
        val task = reference.putBytes(challengePhoto)
        return task.addOnFailureListener { exception ->
            Log.i("Log_tag", "exception add challengePhoto challenge ${exception.message}")
        }.continueWith {
            it.isSuccessful
        }.await()

    }

    suspend fun uploadChallengePhoto(creatorId: String, challengeId: String, file: File): Bitmap =
        ChallengeUtils().generateChallengePhotoReference(creatorId, challengeId).getFile(file)
            .continueWith {
                BitmapFactory.decodeFile(file.path)
            }.await()


    suspend fun deleteChallengeById(challengeId: String, creatorId: String): Boolean {
        return firestore.document(challengeId).delete().continueWith {
            it.isSuccessful && it.isComplete
        }.await() && ChallengeUtils().generateChallengePhotoReference(creatorId, challengeId)
            .delete()
            .continueWith {
                it.isSuccessful && it.isComplete
            }.await()
    }
}