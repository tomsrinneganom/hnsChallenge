package com.dedstudio.hnschallenge.repository.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dedstudio.hnschallenge.Profile
import javax.inject.Singleton

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProfile(vararg profiles:Profile)

    @Query("SELECT * FROM Profile WHERE id = :id")
    fun getProfile(id:String):Profile

    @Update
    fun update(vararg profile:Profile)
}