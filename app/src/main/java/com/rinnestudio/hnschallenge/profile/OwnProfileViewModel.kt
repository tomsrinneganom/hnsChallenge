package com.rinnestudio.hnschallenge.profile

import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
open class OwnProfileViewModel @Inject constructor(
    private val roomDatabase: RoomDatabase
) : ViewModel() {

    suspend fun getProfile() = ProfileRepository().getOwnProfile(roomDatabase)

}