package com.dedstudio.hnschallenge

import androidx.lifecycle.ViewModel
import com.dedstudio.hnschallenge.repository.ProfileRepository

class MainSearchViewModel : ViewModel() {

    suspend fun getRecommendedListOfProfiles(): List<Profile> = ProfileRepository().getRecommendedListOfProfiles()

}