package com.dedstudio.hnschallenge

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedstudio.hnschallenge.repository.ProfileRepository
import com.dedstudio.hnschallenge.repository.room.RoomDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OtherProfileViewModel @ViewModelInject constructor(
    private val roomDatabase: RoomDatabase,
) : ViewModel() {
    private var subscribe: Boolean = false

    private fun updateOwnProfile(profileId: String) {
        val profileRepository = ProfileRepository()
        viewModelScope.launch(Dispatchers.IO) {
            val profile = profileRepository.getOwnProfile(roomDatabase)
            if (checkSubscriptions(profile.subscription, profileId)) {
                profileRepository.updateOwnProfile(roomDatabase, profile)
            }
        }
    }

    private fun updateProfile(profile: Profile) {
        val mId = Firebase.auth.uid
        if (!(mId.isNullOrEmpty()) && profile.id.isNotEmpty()) {
            if (checkSubscriptions(profile.subscribers, mId)) {
                ProfileRepository().updateProfile(profile)
                updateOwnProfile(profile.id)
            }
        }
    }

    private fun checkSubscriptions(
        subscription: MutableList<String>,
        verificationId: String
    ): Boolean {
        return if (subscribe && !subscription.contains(verificationId)) {
            subscription.add(verificationId)
            true
        } else if (!subscribe && subscription.contains(verificationId)) {
            subscription.remove(verificationId)
            true
        } else {
            false
        }
    }

    fun subscribe(profile: Profile, subscribe: Boolean) {
        this.subscribe = subscribe
        updateProfile(profile)
    }

}