package com.rinnestudio.hnschallenge.repository.room.repository

import com.rinnestudio.hnschallenge.profile.Profile
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRoomRepository(private val roomDatabase: RoomDatabase) {

    suspend fun addProfile(profile: Profile) {
        withContext(Dispatchers.IO) {
            roomDatabase.getProfileDao().addProfile(profile)
        }
    }

    fun getProfileById(id: String): Profile {
        return roomDatabase.getProfileDao().getProfile(id)
    }
}