package com.rinnestudio.hnschallenge.profile

import androidx.room.TypeConverter
import java.util.stream.Collectors

class ProfileConverter {

    @TypeConverter
    fun stringListToString(list:MutableList<String>):String {
        //TODO()
        return if (list.isNullOrEmpty()) {
            ""
        } else {
            list.stream().collect(Collectors.joining(", "))
        }
    }

    @TypeConverter
    fun stringToStringList(string: String): MutableList<String> {
        //TODO()
        return if(string.isEmpty()){
            mutableListOf()
        }else{
            string.split(", ").toMutableList()
        }
    }
}