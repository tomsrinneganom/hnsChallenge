package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rinnestudio.hnschallenge.repository.ChallengeRepository

class MapChallengeExecutionViewModel : ViewModel() {
   fun deleteChallenge(challengeId: String, creatorId: String) = liveData {
        emit(ChallengeRepository().deleteChallenge(challengeId, creatorId))
    }
}