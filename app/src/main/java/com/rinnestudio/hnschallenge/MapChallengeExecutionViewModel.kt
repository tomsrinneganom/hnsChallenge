package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ChallengeRepository

class MapChallengeExecutionViewModel : ViewModel() {
    suspend fun deleteChallenge(challengeId: String, creatorId:String) = ChallengeRepository().deleteChallenge(challengeId,creatorId)
}