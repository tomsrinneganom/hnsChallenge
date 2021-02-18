package com.rinnestudio.hnschallenge.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.rinnestudio.hnschallenge.Challenge
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import kotlinx.coroutines.flow.first

open class OtherProfileMapViewModel @ViewModelInject constructor(
    roomDatabase: RoomDatabase,
) : ViewModel() {
    //TODO()
    val profileId = MutableLiveData<String>()

    suspend fun getChallenges(): List<Challenge> {
        return ChallengeRepository().getChallengesByCreatorId(profileId.asFlow().first())

    }
}