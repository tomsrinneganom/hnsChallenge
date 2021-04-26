package com.rinnestudio.hnschallenge.profile

import androidx.lifecycle.*
import com.rinnestudio.hnschallenge.Challenge
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class OtherProfileMapViewModel : ViewModel() {
    private val challenges = MutableLiveData<List<Challenge>>()
    val profileId = MutableLiveData<String>()

    fun getChallenges(): LiveData<List<Challenge>> {
        viewModelScope.launch {
            profileId.asFlow().collect {id->
                challenges.value = ChallengeRepository().getChallengesByCreatorId(id)
            }
        }
        return challenges
    }
}