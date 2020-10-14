package com.dedstudio.hnschallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class MainProfileViewModel : ViewModel() {
    val firestore = Firebase.firestore
    private val profile = MutableLiveData<Profile>()

    fun getProfile(userId: String): LiveData<Profile> {
        if (profile.value == null) {
            firestore.collection("userList").document(userId).get().addOnSuccessListener {
                profile.value = it.toObject<Profile>()
                Log.i("Log_tag", "get profile success")
                if (it.data == null) {
                    Log.i("Log_tag", "get profile null")
                }
//                getSubscribers()
            }.addOnFailureListener {
                Log.i("Log_tag", "get profile failure exception:${it}")
            }
        }
        return profile
    }

    fun subscribeToUser(id: String) {
        val mId = Firebase.auth.uid
        profile.value?.subscription?.add(id)
        val updatedData = profile.value?.subscription
        mId?.let {
            firestore.collection("userList").document(it)
                .update(mapOf("subscription" to updatedData)).addOnSuccessListener {
                Log.i("Log_tag", "success subscribe to user")
            }.addOnFailureListener {
                Log.i("Log_tag", "failure subscribe to user exception:${it.message}")
            }
        }
    }

    fun getSubscribers() {
        Log.i("Log_tag", "getSubscribers")
        if(profile.value?.subscription != null) {
            firestore.collection("userList")
                .whereIn("subscription", profile.value!!.subscription).get()
                .addOnSuccessListener {
                    Log.i("Log_tag", "Success")
                    val profiles = it.toObjects<Profile>()
                    profiles.forEach {
                        Log.i("Log_tag", "${it.id}")
                    }
                }
                .addOnFailureListener {
                    Log.i("Log_tag", "error ${it.message}")

                }
        }
    }
}