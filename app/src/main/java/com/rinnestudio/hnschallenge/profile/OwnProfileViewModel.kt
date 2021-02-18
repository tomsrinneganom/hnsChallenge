package com.rinnestudio.hnschallenge.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.rinnestudio.hnschallenge.repository.ProfileRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import javax.inject.Inject

open class OwnProfileViewModel @ViewModelInject constructor(
    private val roomDatabase: RoomDatabase
) : ViewModel() {

    suspend fun getProfile() = ProfileRepository().getOwnProfile(roomDatabase)

}