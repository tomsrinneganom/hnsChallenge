package com.dedstudio.hnschallenge

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedstudio.hnschallenge.repository.ProfileRepository
import com.dedstudio.hnschallenge.repository.room.RoomDatabase
import kotlinx.coroutines.launch

class SignInViewModel @ViewModelInject constructor(private val roomDatabase: RoomDatabase) :
    ViewModel() {

    suspend fun signInWithEmail(email: String, password: String): Boolean {
       return ProfileRepository().signInWithEmail(email, password, roomDatabase)
    }

    suspend fun signInWithGoogle(idToken: String): Boolean {
        return ProfileRepository().signInWithGoogleAccount(idToken, roomDatabase)
    }
}