package com.rinnestudio.hnschallenge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import com.rinnestudio.hnschallenge.utils.ChallengeUtils

class ChallengeListViewModel(application: Application) : AndroidViewModel(application) {
    suspend fun getChallenges(): List<Challenge> {
        return ChallengeUtils().sortChallengesByDistance(
            ChallengeRepository().getSubscriptionsChallengeList(),
            getApplication<Application>().applicationContext)
    }
}
