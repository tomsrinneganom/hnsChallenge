package com.dedstudio.hnschallenge.repository

import com.dedstudio.hnschallenge.Challenge

class ChallengeRepository {
    private val firebaseRepository = ChallengeFirebaseRepository()
    fun getChallengeById() {

    }

    fun getChallengesByProfileId() {

    }

    fun addChallenge(challenge: Challenge, image: ByteArray) {
        firebaseRepository.addChallenge(challenge, image)
    }

    fun deleteChallengeById() {

    }
}