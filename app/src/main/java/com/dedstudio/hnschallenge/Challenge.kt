package com.dedstudio.hnschallenge

import android.os.Parcel
import android.os.Parcelable

data class Challenge(
    var id:String? = null,
    var latitude:Double? = null,
    var longitude:Double? = null,
    var photoReference:String? = null,
    var creatorName:String? = null,
    var creatorProfilePhotoReference:String? = null,
    var creatorId:String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeString(photoReference)
        parcel.writeString(creatorName)
        parcel.writeString(creatorProfilePhotoReference)
        parcel.writeString(creatorId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Challenge> {
        override fun createFromParcel(parcel: Parcel): Challenge {
            return Challenge(parcel)
        }

        override fun newArray(size: Int): Array<Challenge?> {
            return arrayOfNulls(size)
        }
    }
}