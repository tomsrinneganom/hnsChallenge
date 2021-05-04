package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel

open class ChallengeListViewModel: ViewModel() {
    var deletedChallenge = mutableListOf<Challenge>()

}
