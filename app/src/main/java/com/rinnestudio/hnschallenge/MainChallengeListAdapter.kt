package com.rinnestudio.hnschallenge

import android.content.Context
import androidx.navigation.NavDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainChallengeListAdapter(challenges: MutableList<Challenge>, context: Context) :
    AbstractChallengeListAdapter(challenges, context) {

    override fun createNavDirecetionsToChallenge(challenge: Challenge): NavDirections {
        return MainChallengeListFragmentDirections.actionChallengeListNavigationItemToMapChallengeExecutionFragment(
            challenge
        )
    }

    override fun createNavDirectionsToProfile(creatorId: String): NavDirections {
        return if (creatorId == Firebase.auth.uid) {
            MainChallengeListFragmentDirections.actionChallengeListNavigationItemToOwnProfileNavigationItem()
        } else {
            MainChallengeListFragmentDirections.actionChallengeListNavigationItemToOtherProfileFragment(
                null, creatorId)
        }
    }

}