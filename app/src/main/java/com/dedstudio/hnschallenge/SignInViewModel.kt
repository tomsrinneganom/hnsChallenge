package com.dedstudio.hnschallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dedstudio.hnschallenge.repository.ProfileRepository

class SignInViewModel : ViewModel() {
    fun signInWithEmail(email: String, password: String): LiveData<Boolean> {
        return ProfileRepository().signInWithEmail(email, password)
    }

    fun signInWithGoogle(idToken: String) {
        return ProfileRepository().signInWithGoogleAccount(idToken)
    }
}