package com.dedstudio.hnschallenge

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dedstudio.hnschallenge.repository.ProfileRepository
import com.dedstudio.hnschallenge.repository.room.RoomDatabase
import com.dedstudio.hnschallenge.utils.ImageUtils


class SignUpViewModel @ViewModelInject constructor(
    private val roomDatabase: RoomDatabase,
) : ViewModel() {
    val profilePhoto = MutableLiveData<Bitmap>()

    suspend fun signUp(email: String, userName: String, password: String): Boolean {
        return if (profilePhoto.value == null) {
            ProfileRepository().signUp(email,
                userName,
                password,
                roomDatabase = roomDatabase)
        } else {
            ProfileRepository().signUp(email,
                userName,
                password,
                ImageUtils().convertBitmapToByteArray(profilePhoto.value!!),
                roomDatabase)
        }
    }
}