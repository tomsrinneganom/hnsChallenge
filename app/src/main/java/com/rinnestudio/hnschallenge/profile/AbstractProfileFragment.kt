package com.rinnestudio.hnschallenge.profile

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rinnestudio.hnschallenge.profile.Profile
import com.rinnestudio.hnschallenge.utils.ImageUtils

abstract class AbstractProfileFragment : Fragment() {
    protected lateinit var profile: Profile

    protected lateinit var usernameTextView: TextView
    protected lateinit var scoreTextView: TextView
    protected lateinit var subscriptionTextView: TextView
    protected lateinit var subscribersTextView: TextView
    protected lateinit var photoProfileImageView: ImageView

    protected abstract fun gettingProfile()

    protected fun updateUI() {
        usernameTextView.text = profile.userName
        scoreTextView.text = profile.score.toString()
        subscribersTextView.text = profile.subscribers.size.toString()
        subscriptionTextView.text = profile.subscription.size.toString()
        Log.i("Log_tag", "${profile.photoReference}")
        ImageUtils().uploadProfilePhotoIntoImageView(profile.id,
            profile.photoReference,
            photoProfileImageView)
    }

    protected abstract fun navigateToSubscribersList()
    protected abstract fun navigateToSubscriptionList()

}