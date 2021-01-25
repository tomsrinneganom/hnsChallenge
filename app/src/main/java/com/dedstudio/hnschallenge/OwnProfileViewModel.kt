package com.dedstudio.hnschallenge

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.dedstudio.hnschallenge.repository.ProfileRepository
import com.dedstudio.hnschallenge.repository.room.RoomDatabase

open class OwnProfileViewModel @ViewModelInject constructor(
    private val roomDatabase: RoomDatabase
) : ViewModel() {

    suspend fun getProfile() = ProfileRepository().getOwnProfile(roomDatabase)

}