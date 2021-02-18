package com.rinnestudio.hnschallenge.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rinnestudio.hnschallenge.profile.Profile

@Database(entities = [Profile::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun getProfileDao():ProfileDao

}