package com.dedstudio.hnschallenge.utils

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class ProfileUtils {
    fun getProfilePhotoReference(profileId: String): StorageReference = Firebase.storage.reference.child("$profileId/profilePhoto.jpg")
}