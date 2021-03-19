package com.rinnestudio.hnschallenge

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import com.rinnestudio.hnschallenge.utils.ChallengeUtils
import com.rinnestudio.hnschallenge.utils.LocationUtils

class ChallengeListViewModel(application: Application) : AndroidViewModel(application) {
    suspend fun getChallenges(): List<Challenge> {
        return ChallengeUtils().sortChallengesByDistance(
            ChallengeRepository().getOwnChallengeList(),
            getApplication<Application>().applicationContext)
    }
}
