package com.rinnestudio.hnschallenge.utils

import androidx.navigation.NavDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.rinnestudio.hnschallenge.ChallengeListFragmentDirections

class ProfileUtils {
    fun getProfilePhotoReference(profileId: String): StorageReference = Firebase.storage.reference.child("$profileId/profilePhoto.jpg")

}