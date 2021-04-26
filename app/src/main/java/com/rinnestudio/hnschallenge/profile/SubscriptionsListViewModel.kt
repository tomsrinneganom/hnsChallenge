package com.rinnestudio.hnschallenge.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import kotlinx.coroutines.launch

class SubscriptionsListViewModel : ViewModel() {
    private val profileList = MutableLiveData<List<Profile>>()
    fun getProfileList(idList: List<String>): LiveData<List<Profile>> {
        viewModelScope.launch {
            profileList.value = ProfileRepository().getListOfProfilesById(idList)
        }

        return profileList
    }

}