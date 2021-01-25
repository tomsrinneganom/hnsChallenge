package com.dedstudio.hnschallenge.repository.room.repository

import androidx.lifecycle.LiveData
import com.dedstudio.hnschallenge.Profile
import com.dedstudio.hnschallenge.repository.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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