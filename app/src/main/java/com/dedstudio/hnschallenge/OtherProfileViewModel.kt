package com.dedstudio.hnschallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OtherProfileViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    fun subscribeToUser(profile: Profile) {
        val mId = Firebase.auth.uid
        if (mId != null && profile.id != null && !(profile.subscription.contains(mId))) {
            profile.subscription.add(mId)
            val updatedData = profile.subscription
            firestore.collection("userList").document(profile.id!!)
                .update(mapOf("subscription" to updatedData)).addOnSuccessListener {
                    Log.i("Log_tag", "success subscribe to user")
                }.addOnFailureListener {
                    Log.i("Log_tag", "failure subscribe to user exception:${it.message}")
                }
        }
    }
    fun unsubscribeToUser(profile: Profile) {
        val mId = Firebase.auth.uid
        if (mId != null && profile.id != null && profile.subscription.contains(mId)) {
            profile.subscription.remove(mId)
            val updatedData = profile.subscription
            firestore.collection("userList").document(profile.id!!)
                .update(mapOf("subscription" to updatedData)).addOnSuccessListener {
                    Log.i("Log_tag", "success unsubscribe to user")
                }.addOnFailureListener {
                    Log.i("Log_tag", "failure subscribe to user exception:${it.message}")
                }
        }
    }
}
