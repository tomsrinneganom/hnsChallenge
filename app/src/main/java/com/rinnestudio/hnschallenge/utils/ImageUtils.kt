package com.rinnestudio.hnschallenge.utils

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ImageUtils {
    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        return stream.toByteArray()
    }

    fun uploadProfilePhotoIntoImageView(
        profileId: String,
        photoReference: String?,
        imageView: ImageView,
    ) {
        if (photoReference.isNullOrEmpty()) {
            val storagePhotoReference = ProfileUtils().getProfilePhotoReference(profileId)
            Glide.with(imageView).load(storagePhotoReference).into(imageView)
        } else {
            Glide.with(imageView).load(photoReference).into(imageView)
        }
    }

    fun uploadChallengePhotoIntoImageView(reference: String, imageView: ImageView) {
        val storageReference = Firebase.storage.reference.child(reference)
        Glide.with(imageView).load(storageReference).into(imageView)
    }
}