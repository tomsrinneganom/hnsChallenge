package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.profile.Profile
import com.rinnestudio.hnschallenge.repository.ProfileRepository

class MainSearchViewModel : ViewModel() {

    suspend fun getRecommendedListOfProfiles(): List<Profile> = ProfileRepository().getRecommendedListOfProfiles()

}