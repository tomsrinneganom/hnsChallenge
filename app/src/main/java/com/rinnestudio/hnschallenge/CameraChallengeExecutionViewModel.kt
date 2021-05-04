package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CameraChallengeExecutionViewModel : ViewModel() {
    fun uploadChallengePhoto(creatorID: String, challengeID: String) = liveData {
        emit(ChallengeRepository().uploadChallengePhoto(creatorID, challengeID))
    }
    fun addPoints(){
        GlobalScope.launch {
            ProfileRepository().addPoints()
        }
    }
}