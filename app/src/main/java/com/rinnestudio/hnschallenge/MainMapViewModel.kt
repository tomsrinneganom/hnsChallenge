package com.rinnestudio.hnschallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import kotlinx.coroutines.launch

class MainMapViewModel : ViewModel() {
    private val challenges = MutableLiveData<List<Challenge>>()

    fun getChallenges(): LiveData<List<Challenge>>  {
        viewModelScope.launch {
            challenges.value = ChallengeRepository().getSubscriptionsChallengeList()
        }
        return challenges
    }
}