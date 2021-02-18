package com.rinnestudio.hnschallenge.repository.firebaseRepository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rinnestudio.hnschallenge.profile.Profile
import com.rinnestudio.hnschallenge.utils.ProfileUtils
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class ProfileFirebaseRepository {

    suspend fun getProfile(profileId: String): Profile {
        return Firebase.firestore.collection("userList").document(profileId).get()
            .addOnFailureListener {
                Log.i("Log_tag", "get profile failure exception:${it}")
            }.continueWith {
                if (it.isComplete && it.isSuccessful) {
                    val profile = it.result.toObject<Profile>()
                    if (profile?.id.isNullOrEmpty()) {
                        Profile()
                    } else {
                        profile
                    }
                } else {
                    Profile()
                }
            }.await()!!
    }

    suspend fun signInWithEmail(email: String, password: String): Boolean {
        val auth = Firebase.auth
        return auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener {

            }.continueWith {
                it.isSuccessful && it.isComplete
            }.await()
    }

    //TODO()
    suspend fun signInWithGoogleAccount(idToken: String): Profile {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val auth = Firebase.auth
        val result = MutableLiveData<Profile>()

        val signIn = auth.signInWithCredential(credential).continueWith {
            it.isSuccessful && it.isComplete
        }.await()

        if (signIn) {
            val user = auth.currentUser
            if (user != null) {
                val profile = getProfile(user.uid)
                if (profile.id.isEmpty()) {
                    val newProfile = Profile(
                        user.uid,
                        user.displayName,
                        user.email,
                        user.photoUrl.toString()
                    )

                    if (addProfileToDatabase(newProfile, ByteArray(0))) {
                        return newProfile
                    } else {
                        return Profile()
                    }
                }
                return profile
            }
        }
        return Profile()
    }

    //TODO()!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
            auth.createUserWithEmailAndPassword(profile.email!!, password).addOnCompleteListener {
            }.addOnFailureListener {
                Log.i("Log_tag", "signUp() exception: ${it.message}")
            }.continueWith {
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
        return Firebase.firestore.collection("userList").document(profile.id).set(profile)
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
        Firebase.firestore.collection("userList").document(profile.id).set(profile)
            .addOnSuccessListener {
                Timber.tag("Log_tag").i("updateProfile() success")
            }.addOnFailureListener {
                Timber.tag("Log_tag").i("updateProfile() exception: ${it.message}")
            }
    }

    suspend fun getRecommendedListOfProfiles(): List<Profile> =
        Firebase.firestore.collection("userList")
            .get()
            .continueWith {
                if (it.isSuccessful && it.isComplete) {
                    return@continueWith it.result.toObjects<Profile>()
                }
                listOf()
            }.await()

    suspend fun getListOfProfilesById(idList: List<String>): List<Profile> =
        Firebase.firestore.collection("userList")
            .whereIn("id", idList)
            .get()
            .continueWith {
            if (it.isComplete && it.isSuccessful) {
                it.result.toObjects<Profile>()
            } else {
                listOf()
            }
        }.await()

    fun deleteProfile() {

    }
}