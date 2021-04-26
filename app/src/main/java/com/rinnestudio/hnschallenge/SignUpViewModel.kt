package com.rinnestudio.hnschallenge

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import com.rinnestudio.hnschallenge.utils.ImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val roomDatabase: RoomDatabase,
) : ViewModel() {
    val profilePhoto = MutableLiveData<Bitmap>()


    fun signUp(email: String, userName: String, password: String) = liveData {
        if (profilePhoto.value == null) {
            emit(ProfileRepository().signUp(email,
                userName,
                password,
                roomDatabase = roomDatabase))
        } else {
            emit(ProfileRepository().signUp(email,
                userName,
                password,
                ImageUtils().convertBitmapToByteArray(profilePhoto.value!!),
                roomDatabase))
        }
    }
}