package com.dedstudio.hnschallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class SubscriptionsListViewModel : ViewModel() {
    private val profileList = MutableLiveData<List<Profile>>()

    fun getProfileList(idList: List<String>): LiveData<List<Profile>> {
        val firestore = Firebase.firestore
        firestore.collection("userList")
            .whereIn("id", idList)
            .get()
            .addOnSuccessListener {
                profileList.value = it.toObjects()
            }
        return profileList
    }
}