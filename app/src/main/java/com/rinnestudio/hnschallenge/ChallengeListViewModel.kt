package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ChallengeRepository

class ChallengeListViewModel : ViewModel() {
    suspend fun getChallenges() = ChallengeRepository().getOwnChallengeList()

}
