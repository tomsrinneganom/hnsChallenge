package com.dedstudio.hnschallenge

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dedstudio.hnschallenge.repository.ChallengeRepository
import com.dedstudio.hnschallenge.repository.ProfileRepository
import com.dedstudio.hnschallenge.repository.room.RoomDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class MainMapViewModel @ViewModelInject constructor(
    private val roomDatabase: RoomDatabase
) : ViewModel() {

    suspend fun getChallenges() = ChallengeRepository().getOwnChallengeList()

}