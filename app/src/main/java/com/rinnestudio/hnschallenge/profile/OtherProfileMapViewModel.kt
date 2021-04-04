package com.rinnestudio.hnschallenge.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.rinnestudio.hnschallenge.Challenge
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import kotlinx.coroutines.flow.first

open class OtherProfileMapViewModel: ViewModel() {
    //TODO()
    val profileId = MutableLiveData<String>()

    suspend fun getChallenges(): List<Challenge> {
        return ChallengeRepository().getChallengesByCreatorId(profileId.asFlow().first())

    }
}