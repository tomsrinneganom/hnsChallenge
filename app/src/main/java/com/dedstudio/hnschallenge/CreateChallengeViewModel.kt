package com.dedstudio.hnschallenge

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.dedstudio.hnschallenge.repository.ChallengeRepository
import com.dedstudio.hnschallenge.repository.ProfileRepository
import com.dedstudio.hnschallenge.repository.room.RoomDatabase
import com.dedstudio.hnschallenge.utils.ImageUtils
import com.dedstudio.hnschallenge.utils.LocationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class CreateChallengeViewModel @ViewModelInject constructor(
    application: Application,
    private val roomDatabase: RoomDatabase,
) : AndroidViewModel(application) {

    suspend fun createChallenge(photo: Bitmap): Boolean {
        val challengePhoto = ImageUtils().convertBitmapToByteArray(photo)
        val repository = ChallengeRepository()
        var result = false
        val location = LocationUtils().getLastKnowLocation(getApplication())
        withContext(Dispatchers.IO) {
            val profile = ProfileRepository().getOwnProfile(roomDatabase)
            if (location != null && profile.id.isNotEmpty()) {
                result = repository.createChallenge(profile, location, challengePhoto)
            }
        }
        return result
    }
}