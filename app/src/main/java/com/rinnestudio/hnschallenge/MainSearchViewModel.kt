package com.rinnestudio.hnschallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rinnestudio.hnschallenge.repository.ProfileRepository

class MainSearchViewModel : ViewModel() {

    fun getRecommendedListOfProfiles(): LiveData<List<Profile>> = liveData {
        emit(ProfileRepository().getRecommendedListOfProfiles())
    }

}