package com.rinnestudio.hnschallenge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import com.rinnestudio.hnschallenge.utils.ChallengeUtils

class MainChallengeListViewModel(application: Application) : AndroidViewModel(application) {

    fun getChallenges(): LiveData<List<Challenge>> = liveData {
        emitSource(ChallengeUtils().sortChallengesByDistance(
            ChallengeRepository().getSubscriptionsChallengeList(),
            getApplication<Application>().applicationContext))
    }

}
