package com.rinnestudio.hnschallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val roomDatabase: RoomDatabase) :
    ViewModel() {

    fun signInWithEmail(email: String, password: String) = liveData {
        emit(ProfileRepository().signInWithEmail(email, password, roomDatabase))
    }

    fun signInWithGoogle(idToken: String) = liveData {
        emit(ProfileRepository().signInWithGoogleAccount(idToken, roomDatabase))
    }
}