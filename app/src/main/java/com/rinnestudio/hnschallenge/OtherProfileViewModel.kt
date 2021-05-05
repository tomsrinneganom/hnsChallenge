package com.rinnestudio.hnschallenge

import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherProfileViewModel @Inject constructor(
    private val roomDatabase: RoomDatabase,
) : ViewModel() {

    private var profile = MutableLiveData<Profile>()
    private lateinit var ownProfile: Profile

    fun setProfile(profile: Profile) {
        this.profile.value = profile
    }

    fun getProfile(profileId: String): LiveData<Profile> {
        viewModelScope.launch {
            profile.value = ProfileRepository().getProfile(profileId)
        }
        return profile
    }

    fun subscribe() = liveData {
        if (profile.value != null && getOwnProfile()) {
            if (!checkSubscriptions()) {
                profile.value!!.subscribers.add(ownProfile.id)
                ownProfile.subscription.add(profile.value!!.id)
                updateProfiles()
                emit(true)
            } else
                emit(false)
        } else
            emit(false)
    }

    fun unSubscribe() = liveData {
        if (profile.value != null && getOwnProfile()) {
            if (checkSubscriptions()) {
                profile.value!!.subscribers.remove(ownProfile.id)
                ownProfile.subscription.remove(profile.value!!.id)
                updateProfiles()
                emit(true)
            } else
                emit(false)
        }else
            emit(false)
    }

    private suspend fun getOwnProfile(): Boolean = coroutineScope {
        val id = Firebase.auth.uid
        if (id != null) {
            val profileRepository = ProfileRepository()
            ownProfile = profileRepository.getOwnProfile(roomDatabase)
            true
        } else {
            false
        }
    }

    private fun checkSubscriptions() = profile.value!!.subscribers.contains(ownProfile.id) &&
            ownProfile.subscription.contains(profile.value!!.id)

    private fun updateProfiles() {
        val profileRepository = ProfileRepository()
        profileRepository.updateProfile(profile.value!!)
        profileRepository.updateOwnProfile(roomDatabase, ownProfile)
    }
}