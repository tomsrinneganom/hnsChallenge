package com.rinnestudio.hnschallenge

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase

class MainMapViewModel @ViewModelInject constructor(
    private val roomDatabase: RoomDatabase
) : ViewModel() {

    suspend fun getChallenges() = ChallengeRepository().getOwnChallengeList()

}