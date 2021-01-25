package com.dedstudio.hnschallenge.utils

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

class ImageUtils {
    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        val b1 = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        return stream.toByteArray()
    }

    fun uploadImageIntoImageView(profileId: String, photoReference: String?, imageView: ImageView) {
        if (photoReference.isNullOrEmpty()) {
            val storagePhotoReference = ProfileUtils().getProfilePhotoReference(profileId)
            Glide.with(imageView).load(storagePhotoReference).into(imageView)
        } else {
            Glide.with(imageView).load(photoReference).into(imageView)

        }
    }
}