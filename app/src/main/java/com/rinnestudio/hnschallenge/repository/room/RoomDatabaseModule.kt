package com.rinnestudio.hnschallenge.repository.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityRetainedComponent::class)
object RoomDatabaseModule{

    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ):RoomDatabase {
       return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "hnsChallenge.db"
        ).build()
    }
}