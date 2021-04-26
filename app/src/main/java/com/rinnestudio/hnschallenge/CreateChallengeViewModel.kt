package com.rinnestudio.hnschallenge

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.*
import com.rinnestudio.hnschallenge.repository.ChallengeRepository
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import com.rinnestudio.hnschallenge.utils.ImageUtils
import com.rinnestudio.hnschallenge.utils.LocationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    application: Application,
    private val roomDatabase: RoomDatabase,
) : AndroidViewModel(application) {

    fun createChallenge(photo: Bitmap): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val challengePhoto = ImageUtils().convertBitmapToByteArray(photo)
            val repository = ChallengeRepository()
            val location = LocationUtils().getUserLocation(getApplication())
            location.asFlow().collect {
                val profile = ProfileRepository().getOwnProfile(roomDatabase)

                if (it != null && profile.id.isNotEmpty()) {
                    result.value = repository.createChallenge(profile, it, challengePhoto)
                } else {
                    result.value = false
                }
            }
        }
        return result
    }
}