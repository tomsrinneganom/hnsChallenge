package com.rinnestudio.hnschallenge.repository.firebaseRepository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.rinnestudio.hnschallenge.Challenge
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rinnestudio.hnschallenge.utils.ChallengeUtils
import kotlinx.coroutines.tasks.await
import java.io.File

class ChallengeFirebaseRepository {
    private val firestore = Firebase.firestore.collection("challenges").document("users")

    suspend fun getChallengesByCreatorId(creatorId: String): List<Challenge> {
        return firestore.collection(creatorId).get().continueWith {
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
        val reference =
            ChallengeUtils().generateChallengePhotoReference(challenge.creatorId!!, challenge.id!!)
        val task = reference.putBytes(challengePhoto)
        //TODO() create new fun for upload challenge photo
        val uploadChallengeImage = task.addOnSuccessListener {
            Log.i("Log_tag", "SUCCESSS add challengePhoto challenge")

        }.addOnFailureListener { exception ->
            Log.i("Log_tag", "exception add challengePhoto challenge ${exception.message}")
        }.continueWith {
            it.isSuccessful
        }.await()

        val uploadChallengeInfo = firestore.collection(challenge.creatorId!!)
            .document(challenge.id!!)
            .set(challenge)
            .addOnSuccessListener {
                Log.i("Log_tag", "SUCCESSS add challenge info")
            }.addOnFailureListener { exception ->
                Log.i("Log_tag", "${exception.message}")
            }.continueWith {
                it.isSuccessful
            }
            .await()

        return uploadChallengeInfo && uploadChallengeImage
    }

    //    suspend fun getOwnChallengeList(): List<Challenge> {
//        return firestore
//            .collection(Firebase.auth.currentUser!!.uid).get().continueWith {
//                if (it.isComplete && it.isSuccessful) {
//                    it.result.toObjects<Challenge>()
//                } else {
//                    listOf()
//                }
//            }.await()
//
//    }
//    suspend fun getOwnChallengeList(subscriptionList: MutableList<String>): List<Challenge> {
//
//    }

    suspend fun uploadChallengePhoto(creatorId: String, challengeId: String, file: File): Bitmap =
        ChallengeUtils().generateChallengePhotoReference(creatorId, challengeId).getFile(file)
            .continueWith {
                BitmapFactory.decodeFile(file.path)
            }.await()


    suspend fun deleteChallengeById(challengeId: String, creatorId: String): Boolean {
        return firestore.collection(creatorId).document(challengeId).delete().continueWith {
            it.isSuccessful && it.isComplete
        }.await() && ChallengeUtils().generateChallengePhotoReference(creatorId, challengeId)
            .delete()
            .continueWith {
                it.isSuccessful && it.isComplete
            }.await()
    }
}