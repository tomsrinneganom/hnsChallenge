package com.rinnestudio.hnschallenge.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.Profile
import com.rinnestudio.hnschallenge.repository.firebaseRepository.ProfileFirebaseRepository
import com.rinnestudio.hnschallenge.repository.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileRepository {
    val profile = MutableLiveData<Profile>()
    private val firebaseRepository = ProfileFirebaseRepository()

    suspend fun getProfile(profileId: String) = firebaseRepository.getProfile(profileId)

    suspend fun getOwnProfile(roomDatabase: RoomDatabase): Profile {
        val id = Firebase.auth.uid!!
        val profile = getProfile(id)
        withContext(Dispatchers.IO) {
            roomDatabase.getProfileDao().update(profile)
        }
        return profile
    }

    suspend fun signInWithEmail(
        email: String,
        password: String,
        roomDatabase: RoomDatabase,
    ): Boolean {
        val signIn = firebaseRepository.signInWithEmail(email, password)

        return if (signIn) {
            val profile = getProfile(Firebase.auth.currentUser!!.uid)
            if (profile.id.isEmpty()) {
                return false
            }
            addProfileToRoomDatabase(profile, roomDatabase)
            true
        } else {
            false
        }
    }

    suspend fun signInWithGoogleAccount(
        idToken: String,
        roomDatabase: RoomDatabase,
    ): Boolean {
        val profile = firebaseRepository.signInWithGoogleAccount(idToken)
        return if (profile.id.isNotEmpty()) {
            addProfileToRoomDatabase(profile, roomDatabase)
            true
        } else {
            false
        }
    }

    suspend fun signUp(
        email: String,
        username: String,
        password: String,
        image: ByteArray = ByteArray(0),
        roomDatabase: RoomDatabase,
    ): Boolean {
        val profile = Profile(userName = username, email = email)
        val result = firebaseRepository.signUp(profile, password, image)
        if (result) {
            addProfileToRoomDatabase(profile, roomDatabase)
        }
        return result
    }

    fun updateProfile(profile: Profile) {
        firebaseRepository.updateProfile(profile)
    }

    fun updateOwnProfile(roomDatabase: RoomDatabase, profile: Profile) {
        GlobalScope.launch(Dispatchers.IO) {
            roomDatabase.getProfileDao().update(profile)
        }
        updateProfile(profile)

    }

    private suspend fun addProfileToRoomDatabase(profile: Profile, roomDatabase: RoomDatabase) {
        withContext(Dispatchers.IO) {
            roomDatabase.getProfileDao().addProfile(profile)
        }
    }

    suspend fun getRecommendedListOfProfiles() =
        ProfileFirebaseRepository().getRecommendedListOfProfiles()

    suspend fun getListOfProfilesById(idList: List<String>) =
        ProfileFirebaseRepository().getListOfProfilesById(idList)

    suspend fun getSubscriptionList(): List<String> {
        val id = Firebase.auth.uid
        if (id != null) {
            return getProfile(id).subscription
        }
        return emptyList()
    }

    suspend fun addPoints() {
        val ownProfile = getProfile(Firebase.auth.currentUser!!.uid)
        ProfileFirebaseRepository().addPoints(ownProfile)
    }
}