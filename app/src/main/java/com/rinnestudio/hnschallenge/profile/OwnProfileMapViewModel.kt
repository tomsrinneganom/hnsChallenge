package com.rinnestudio.hnschallenge.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.Challenge
import com.rinnestudio.hnschallenge.repository.ChallengeRepository

class OwnProfileMapViewModel : ViewModel() {

    suspend fun getChallenges(): List<Challenge> {
        val id = Firebase.auth.uid
        if (id != null) {
            return ChallengeRepository().getChallengesByCreatorId(id)
        } else {
            return emptyList()
        }
    }
}