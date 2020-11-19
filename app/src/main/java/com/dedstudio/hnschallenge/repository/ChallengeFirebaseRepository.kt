package com.dedstudio.hnschallenge.repository

import android.util.Log
import com.dedstudio.hnschallenge.Challenge
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ChallengeFirebaseRepository {
    private val firestore = Firebase.firestore.collection("challenges").document("users")

    fun getChallengeById() {

    }

    fun getChallengesByProfileId() {

    }

    fun addChallenge(challenge: Challenge, image: ByteArray) {
        val reference =
            Firebase.storage.reference.child("${challenge.creatorId}/${challenge.id}.jpg")
        val task = reference.putBytes(image)
        task.addOnSuccessListener {
            Log.i("Log_tag", "SUCCESSS add image challenge")

        }.addOnFailureListener { exception ->
            Log.i("Log_tag", "exception add image challenge ${exception.message}")
        }

        firestore.collection(challenge.creatorId!!)
            .document(challenge.id!!)
            .set(challenge)
            .addOnSuccessListener {
                Log.i("Log_tag", "SUCCESSS add challenge info")
            }.addOnFailureListener { exception ->
                Log.i("Log_tag", "${exception.message}")
            }

    }

    fun deleteChallengeById() {

    }
}