package com.dedstudio.hnschallenge

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedstudio.hnschallenge.repository.ChallengeRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.File

class CameraChallengeExecutionViewModel : ViewModel() {
    suspend fun uploadChallengePhoto(creatorID: String, challengeID: String) =
        ChallengeRepository().uploadChallengePhoto(creatorID, challengeID)
}