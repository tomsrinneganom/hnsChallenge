package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ChallengeRepository

class MainMapViewModel : ViewModel() {

    suspend fun getChallenges() = ChallengeRepository().getSubscriptionsChallengeList()

}