package com.rinnestudio.hnschallenge

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import com.rinnestudio.hnschallenge.utils.ImageUtils
import javax.inject.Inject


class SignUpViewModel @Inject constructor(
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