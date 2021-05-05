package com.rinnestudio.hnschallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class OwnProfileViewModel @Inject constructor(
    private val roomDatabase: RoomDatabase,
) : ViewModel() {

    private val profile = MutableLiveData<Profile>()

    fun getProfile(): MutableLiveData<Profile> {
        viewModelScope.launch {
            profile.value = ProfileRepository().getOwnProfile(roomDatabase)
        }
        return profile
    }
}