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
import kotlinx.coroutines.tasks.await
import java.io.File

class ChallengeFirebaseRepository {
    private val firestore = Firebase.firestore.collection("challenges").document("users")

    fun getChallengeById() {

    }

    fun getChallengesByProfileId() {

    }

    suspend fun addChallengeToDatabase(challenge: Challenge, challengePhoto: ByteArray): Boolean {
        val reference =
            Firebase.storage.reference.child("${challenge.creatorId}/${challenge.id}.jpg")
        val task = reference.putBytes(challengePhoto)

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

    suspend fun getOwnChallengeList(): List<Challenge> {
        val firestore = Firebase.firestore
       return firestore.collection("challenges").document("users").collection(Firebase.auth.currentUser!!.uid).get().continueWith {
            if(it.isComplete && it.isSuccessful) {
                it.result.toObjects<Challenge>()
            }else{
                listOf()
            }
        }.await()

    }

    suspend fun uploadChallengePhoto(creatorId: String, challengeId: String, file: File): Bitmap =
        getReferenceToChallengePhoto(creatorId, challengeId).getFile(file)
            .continueWith {
                BitmapFactory.decodeFile(file.path)
            }.await()

    private fun getReferenceToChallengePhoto(
        creatorId: String,
        challengeId: String
    ) = Firebase.storage.reference.child("${creatorId}/${challengeId}.jpg")


    fun deleteChallengeById() {

    }
}