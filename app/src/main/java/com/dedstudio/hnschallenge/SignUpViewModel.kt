package com.dedstudio.hnschallenge

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dedstudio.hnschallenge.repository.ProfileRepository


class SignUpViewModel : ViewModel() {
    val profilePhoto = MutableLiveData<Bitmap>()

    fun signUp(email: String, userName: String, password: String): LiveData<Boolean> {
        return if (profilePhoto.value == null) {
            ProfileRepository().signUp(email, userName, password)
        } else {
            ProfileRepository().signUp(email,
                userName,
                password,
                ImageUtils().convertBitmapToByteArray(profilePhoto.value!!))
        }
    }
}