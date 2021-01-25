package com.dedstudio.hnschallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class ProfileMapViewModel : ViewModel() {
    val challenges = MutableLiveData<List<Challenge>>()

    fun getChallenges(): LiveData<List<Challenge>> {
        val firestore = Firebase.firestore
        //TODO() edit collection path
        firestore.collection("challenges").document("users")
            .collection(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                Log.i("Log_tag", "get  challenges success")
                val challengeList: List<Challenge> = it.toObjects()
                challenges.value = challengeList
            }.addOnFailureListener {
                Log.i("Log_tag", "get  challenges exception:${it.message}")
            }
        return challenges
    }
}