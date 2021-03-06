package com.rinnestudio.hnschallenge

import android.os.Parcel
import android.os.Parcelable

data class Challenge(
    var id:String? = null,
    var latitude:Double? = null,
    var longitude:Double? = null,
    var accuracy: Float? = null,
    var photoReference:String? = null,
    var creatorName:String? = null,
    var creatorProfilePhotoReference:String? = null,
    var creatorId:String? = null,
    var distanceToCurrentLocation:Float? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Float
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeValue(accuracy)
        parcel.writeString(photoReference)
        parcel.writeString(creatorName)
        parcel.writeString(creatorProfilePhotoReference)
        parcel.writeString(creatorId)
        parcel.writeValue(distanceToCurrentLocation)
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