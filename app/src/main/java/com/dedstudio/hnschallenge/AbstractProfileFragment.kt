package com.dedstudio.hnschallenge

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

abstract class AbstractProfileFragment : Fragment() {
    protected lateinit var profileId: String
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

        val photoReference = ProfileUtils().getProfilePhotoReference(profile.id!!)
        Glide.with(requireContext()).load(photoReference)
            .into(photoProfileImageView)
    }

    protected abstract fun navigateToSubscribersList()
    protected abstract fun navigateToSubscriptionList()

}