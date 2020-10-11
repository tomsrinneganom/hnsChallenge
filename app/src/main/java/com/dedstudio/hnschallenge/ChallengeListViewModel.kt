package com.dedstudio.hnschallenge

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.ktx.storage

class ChallengeListViewModel : ViewModel() {
    val challenges = MutableLiveData<List<Challenge>>()

    //TODO() copy past
    fun getChallenges(): LiveData<List<Challenge>> {
        getChallengeInfo()
        return challenges
    }

    fun getChallengeInfo() {
        val challengesLiveData = MutableLiveData<List<Challenge>>()
        val firestore = Firebase.firestore
        //TODO edit collection path
        firestore.collection("challenges").document("users").collection(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                Log.i("Log_tag", "get  challenges success")
                val challengesResult: List<Challenge> = it.toObjects()
                challenges.value = challengesResult
                challengesResult.forEach {challenge->
                    Log.i("Log_tag","challenge info :${challenge.id}")

                }
            }.addOnFailureListener {
                Log.i("Log_tag", "get  challenges exception:${it.message}")
            }
    }

    fun getChallengesImage(): LiveData<ListResult> {
        val storage = Firebase.storage
        val ref = storage.reference.child("username")
        val referenceList = MutableLiveData<ListResult>()
        ref.listAll().addOnSuccessListener {
            Log.i("Log_tag","${it.items.size}")
            Log.i("Log_tag","${it.prefixes  .size}")
            it.items.forEach {reference->
                Log.i("Log_tag","image reference: $reference")
            }
            it.prefixes.forEach {reference->
                Log.i("Log_tag","image reference: $reference")
            }
        }
return referenceList
}
}
