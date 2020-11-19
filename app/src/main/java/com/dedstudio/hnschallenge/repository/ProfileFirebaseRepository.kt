package com.dedstudio.hnschallenge.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dedstudio.hnschallenge.Profile
import com.dedstudio.hnschallenge.ProfileUtils
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileFirebaseRepository {
    fun getProfile(profileId: String): LiveData<Profile> {
        val profile = MutableLiveData<Profile>()
        Firebase.firestore.collection("userList").document(profileId).get().addOnSuccessListener {
            Log.i("Log_tag", "get profile success")
            val result = it.toObject<Profile>()
            if (result != null) {
                profile.value = result!!
                Log.i("Log_tag", "get profile null")
            }
        }.addOnFailureListener {
            Log.i("Log_tag", "get profile failure exception:${it}")
        }
        return profile
    }

    fun signInWithEmail(email: String, password: String): LiveData<Boolean> {
        val authResult = MutableLiveData<Boolean>()
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("Log_tag", "isSuccessful == true")
                if (auth.currentUser != null) {
                    getProfile(auth.currentUser!!.uid).observeForever { profile ->
                        authResult.value = profile != null
                    }
                } else {
                    authResult.value = false
                }
            } else {
                authResult.value = false
            }
        }.addOnFailureListener {
            authResult.value = false

            Log.i("Log_tag", "error ${it.message}")

        }
        return authResult
    }

    fun signInWithGoogleAccount(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val auth = Firebase.auth

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Log_tag", "signInWithCredential:success")
                    val user = auth.currentUser
                    if (user != null) {
                        getProfile(user.uid)
                    }
                } else {
                    Log.d("Log_tag", "signInWithCredential:failure", task.exception)
                }
            }.addOnFailureListener {
                Log.d("Log_tag", it.message)

            }

    }

    fun signUp(
        email: String,
        username: String,
        password: String,
        image: ByteArray,
    ): LiveData<Boolean> {
        val auth = Firebase.auth
        var result = MutableLiveData<Boolean>()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.result.user != null) {
                result = createProfile(email, username, it.result.user!!.uid, image)
            } else {
                result.value = false
            }
        }.addOnFailureListener {
            Log.i("Log_tag", "signUp() exception: ${it.message}")
            result.value = false
        }
        return result
    }

    private fun createProfile(
        email: String,
        username: String,
        userId: String,
        image: ByteArray,
    ): MutableLiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        uploadProfilePhoto(userId, image).observeForever { photoReference ->
            val profile = Profile(username, email, userId, photoReference)

            Firebase.firestore.collection("userList").document(userId).set(profile)
                .addOnSuccessListener {
                    Log.i("Log_tag", "sign up success")
                    result.value = true
                }.addOnFailureListener {
                    Log.i("Log_tag", "sign up failure exception:${it}")
                    result.value = false
                }
        }

        return result
    }

    private fun uploadProfilePhoto(userId: String, image: ByteArray): LiveData<String> {
        val pathToPhoto = MutableLiveData<String>()
        if (image.isNotEmpty()) {
            val reference = ProfileUtils().getProfilePhotoReference(userId)
            reference.putBytes(image).addOnSuccessListener {
                pathToPhoto.value = reference.path
            }.addOnFailureListener {
                pathToPhoto.value = ""
            }
        } else {
            pathToPhoto.value = ""
        }
        return pathToPhoto
    }

    fun signInWithGoogle() {

    }

    fun editProfile() {

    }

    fun deleteProfile() {

    }
}