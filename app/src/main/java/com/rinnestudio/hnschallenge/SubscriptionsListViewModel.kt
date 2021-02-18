package com.rinnestudio.hnschallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.profile.Profile
import com.rinnestudio.hnschallenge.repository.ProfileRepository

class SubscriptionsListViewModel : ViewModel() {
    private val profileList = MutableLiveData<List<Profile>>()

    suspend fun getProfileList(idList: List<String>) =
        ProfileRepository().getListOfProfilesById(idList)
}