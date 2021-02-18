package com.rinnestudio.hnschallenge.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import kotlinx.coroutines.flow.collect

class OwnProfileMapViewModel @ViewModelInject constructor(
    roomDatabase: RoomDatabase,
) : ViewModel() {

    suspend fun getChallenges() = ChallengeRepository().getOwnChallengeList()
}