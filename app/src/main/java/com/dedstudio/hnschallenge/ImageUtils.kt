package com.dedstudio.hnschallenge

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class ImageUtils {
    fun convertFileImageToByteArray(pathToImage: String): ByteArray {
        val bitmap = BitmapFactory.decodeFile(pathToImage)
        val stream = ByteArrayOutputStream()
        val b1 = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        return stream.toByteArray()
    }
}