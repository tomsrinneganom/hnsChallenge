package com.dedstudio.hnschallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dedstudio.hnschallenge.Profile

class ProfileRepository {
    val profile = MutableLiveData<Profile>()
    private val firebaseRepository = ProfileFirebaseRepository()

    fun getProfile(profileId:String):LiveData<Profile> {
       return firebaseRepository.getProfile(profileId)
    }

    fun signInWithEmail(email: String, password: String): LiveData<Boolean> {
        return firebaseRepository.signInWithEmail(email, password)
    }

    fun signInWithGoogleAccount(idToken: String) {
        firebaseRepository.signInWithGoogleAccount(idToken)
    }

    fun signUp(email: String, username: String, password: String, image:ByteArray = ByteArray(0)):LiveData<Boolean> {
        return firebaseRepository.signUp(email, username, password, image)
    }

    fun editProfile() {

    }

    fun deleteProfile() {

    }

}