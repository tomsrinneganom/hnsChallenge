package com.rinnestudio.hnschallenge.repository.firebaseRepository

import android.util.Log
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.Profile
import com.rinnestudio.hnschallenge.utils.ProfileUtils
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class ProfileFirebaseRepository {
    private val profilesCollection = Firebase.firestore.collection("userList")

    suspend fun getProfile(profileId: String): Profile =
        profilesCollection.document(profileId).get().continueWith {
            if (it.isComplete && it.isSuccessful) {
                it.result.toObject<Profile>() ?: Profile()
            } else {

                Profile()
            }
        }.await()

    suspend fun signInWithEmail(email: String, password: String): Boolean {
        val auth = Firebase.auth
        return auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener {

            }.continueWith {
                it.isSuccessful && it.isComplete
            }.await()
    }

    suspend fun signInWithGoogleAccount(idToken: String): Profile {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val auth = Firebase.auth

        val signIn = auth.signInWithCredential(credential).continueWith {
            Log.i("Log_tag", "fail :${it.exception} ${it.isComplete}")
            it.isSuccessful && it.isComplete
        }.await()

        if (signIn) {
            val user = auth.currentUser!!
            val profile = getProfile(user.uid)
            if (profile.id.isEmpty()) {
                val newProfile = Profile(
                    user.uid,
                    user.displayName,
                    user.email,
                    user.photoUrl.toString()
                )

                return if (addProfileToDatabase(newProfile, ByteArray(0))) {
                    newProfile
                } else {
                    Profile()
                }
            }
            return profile
        }
        return Profile()
    }

    suspend fun signUp(
        profile: Profile,
        password: String,
        image: ByteArray,
    ): Boolean {
        val auth = Firebase.auth
        if (profile.email.isNullOrEmpty()) {
            return false
        }
        val userCreation =
            auth.createUserWithEmailAndPassword(profile.email!!, password)
                .continueWith {
                    it.isComplete && it.isSuccessful
                }.await()

        return if (userCreation) {
            val user = auth.currentUser
            if (user != null) {
                profile.id = user.uid
                addProfileToDatabase(profile, image)
            } else {
                false
            }
        } else {
            false
        }
    }

    private suspend fun addProfileToDatabase(
        profile: Profile,
        image: ByteArray,
    ): Boolean {
        uploadProfilePhoto(profile.id, image)
        return profilesCollection.document(profile.id).set(profile)
            .continueWith {
                it.isSuccessful
            }.await()
    }

    private suspend fun uploadProfilePhoto(userId: String, image: ByteArray): String {
        if (image.isNotEmpty()) {
            val reference = ProfileUtils().getProfilePhotoReference(userId)

            val uploadPhoto = reference.putBytes(image).continueWith {
                it.isSuccessful
            }.await()
            if (uploadPhoto) {
                return reference.path
            }
        }
        return ""
    }

    fun updateProfile(profile: Profile) {
        profilesCollection.document(profile.id).set(profile)
            .addOnSuccessListener {
                Timber.tag("Log_tag").i("updateProfile() success")
            }.addOnFailureListener {
                Timber.tag("Log_tag").i("updateProfile() exception: ${it.message}")
            }
    }

    suspend fun getRecommendedListOfProfiles(): List<Profile> =
        profilesCollection
            .get().continueWith {
                if (it.isSuccessful && it.isComplete) {
                    it.result.toObjects<Profile>()
                } else
                    emptyList()
            }.await()

    suspend fun getListOfProfilesById(idList: List<String>): List<Profile> =
        profilesCollection
            .whereIn("id", idList)
            .get()
            .continueWith {
                if (it.isComplete && it.isSuccessful) {
                    it.result.toObjects<Profile>()
                } else {
                    emptyList()
                }
            }.await()

    suspend fun addPoints(profile: Profile) {
        profile.score += 100
        profilesCollection.document(profile.id).set(profile).await()
    }
}
