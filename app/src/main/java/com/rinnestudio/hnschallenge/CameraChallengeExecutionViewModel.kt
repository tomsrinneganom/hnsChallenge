package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ChallengeRepository

class CameraChallengeExecutionViewModel : ViewModel() {
    suspend fun uploadChallengePhoto(creatorID: String, challengeID: String) =
        ChallengeRepository().uploadChallengePhoto(creatorID, challengeID)
}