package com.rinnestudio.hnschallenge

import android.content.Context
import androidx.navigation.NavDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SelectedChallengeListAdapter(challenges: List<Challenge>, context: Context) :
    AbstractChallengeListAdapter(challenges.toMutableList(), context) {

    override fun createNavDirectionsToProfile(creatorId: String): NavDirections {
        return if (creatorId == Firebase.auth.uid) {
            SelectedChallengeListFragmentDirections.actionSelectedChallengeListFragmentToOwnProfileNavigationItem()
        } else {
            SelectedChallengeListFragmentDirections.actionSelectedChallengeListFragmentToOtherProfileFragment(
                null, creatorId)
        }
    }

    override fun createNavDirecetionsToChallenge(challenge: Challenge): NavDirections {
        return SelectedChallengeListFragmentDirections.actionSelectedChallengeListFragmentToMapChallengeExecutionFragment(
            challenge
        )
    }
}