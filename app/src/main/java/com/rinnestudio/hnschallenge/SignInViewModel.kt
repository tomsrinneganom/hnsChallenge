package com.rinnestudio.hnschallenge

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase

class SignInViewModel @ViewModelInject constructor(private val roomDatabase: RoomDatabase) :
    ViewModel() {

    suspend fun signInWithEmail(email: String, password: String): Boolean {
       return ProfileRepository().signInWithEmail(email, password, roomDatabase)
    }

    suspend fun signInWithGoogle(idToken: String): Boolean {
        return ProfileRepository().signInWithGoogleAccount(idToken, roomDatabase)
    }
}