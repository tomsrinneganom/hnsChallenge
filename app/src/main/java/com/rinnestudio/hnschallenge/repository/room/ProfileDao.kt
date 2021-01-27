package com.rinnestudio.hnschallenge.repository.room

import androidx.room.*
import com.rinnestudio.hnschallenge.Profile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProfile(vararg profiles:Profile)

    @Query("SELECT * FROM Profile WHERE id = :id")
    fun getProfile(id:String):Profile

    @Update
    fun update(vararg profile:Profile)
}