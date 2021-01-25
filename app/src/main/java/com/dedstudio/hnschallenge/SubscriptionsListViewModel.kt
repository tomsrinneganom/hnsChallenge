package com.dedstudio.hnschallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dedstudio.hnschallenge.repository.ProfileRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class SubscriptionsListViewModel : ViewModel() {
    private val profileList = MutableLiveData<List<Profile>>()

    suspend fun getProfileList(idList: List<String>) =
        ProfileRepository().getListOfProfilesById(idList)
}