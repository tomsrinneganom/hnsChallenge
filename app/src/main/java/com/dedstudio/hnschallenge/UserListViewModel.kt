package com.dedstudio.hnschallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class UserListViewModel : ViewModel() {
    private val profileList = MutableLiveData<List<Profile>>()

    fun getProfileList(idList: List<String>):LiveData<List<Profile>> {
        val firestore = Firebase.firestore
        firestore.collection("userList").whereIn("id", listOf("KYEjm4Q1d0Z5buyM8ke42YgVieO2","91sO3uLEFIZhoL0a0piHQID0WOj2")).get()
            .addOnSuccessListener {
                profileList.value = it.toObjects()
            }
        return profileList
    }
}