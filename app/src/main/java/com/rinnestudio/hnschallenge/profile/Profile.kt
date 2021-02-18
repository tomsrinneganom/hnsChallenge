package com.rinnestudio.hnschallenge.profile

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(ProfileConverter::class)
data class Profile(
    @PrimaryKey var id: String = "",
    var userName: String? = null,
    var email: String? = null,
    var photoReference: String? = null,
    var score: Int = 0,
    var rank: Int = 0,
    var subscribers: MutableList<String> = mutableListOf(),
    var subscription: MutableList<String> = mutableListOf(),
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        mutableListOf<String>().apply {
            parcel.readStringList(this)
        },
        mutableListOf<String>().apply {
            parcel.readStringList(this)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userName)
        parcel.writeString(email)
        parcel.writeString(photoReference)
        parcel.writeInt(score)
        parcel.writeInt(rank)
        parcel.writeStringList(subscribers)
        parcel.writeStringList(subscription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Profile> {
        override fun createFromParcel(parcel: Parcel): Profile {
            return Profile(parcel)
        }

        override fun newArray(size: Int): Array<Profile?> {
            return arrayOfNulls(size)
        }
    }
}