package com.dedstudio.hnschallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class MainSearchViewModel : ViewModel() {
    private val profileList = MutableLiveData<List<Profile>>()

    //TODO: set limit of received items profile list
    fun getProfileList(): LiveData<List<Profile>> {
        val firestore = Firebase.firestore
        firestore.collection("userList")
            .get()
            .addOnSuccessListener {
                profileList.value = it.toObjects()
            }
        return profileList
    }

}