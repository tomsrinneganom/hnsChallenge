package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rinnestudio.hnschallenge.repository.ChallengeRepository

class CameraChallengeExecutionViewModel : ViewModel() {
    fun uploadChallengePhoto(creatorID: String, challengeID: String) = liveData {
        emit(ChallengeRepository().uploadChallengePhoto(creatorID, challengeID))
    }
}