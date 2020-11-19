package com.dedstudio.hnschallenge

import android.os.Parcel
import android.os.Parcelable

data class  Profile(
    var userName:String? = null,
    var email:String? = null,
    var id:String? = null,
    var photoReference:String? = null,
    var score:Int = 0,
    var rank:Int = 0,
    var subscribers:MutableList<String> = mutableListOf(),
    var subscription:MutableList<String> = mutableListOf()
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
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
        parcel.writeString(userName)
        parcel.writeString(email)
        parcel.writeString(id)
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