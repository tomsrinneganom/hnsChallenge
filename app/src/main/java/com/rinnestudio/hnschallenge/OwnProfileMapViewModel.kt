package com.rinnestudio.hnschallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.Challenge
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import kotlinx.coroutines.launch

class OwnProfileMapViewModel : ViewModel() {
    private val challenges = MutableLiveData<List<Challenge>>()

    fun getChallenges(): LiveData<List<Challenge>> {
        val id = Firebase.auth.uid
        if (id != null) {
            viewModelScope.launch {
               challenges.value = ChallengeRepository().getChallengesByCreatorId(id)
            }
        }
        return challenges
    }

}