package com.dedstudio.hnschallenge.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dedstudio.hnschallenge.Profile

@Database(entities = [Profile::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun getProfileDao():ProfileDao

}