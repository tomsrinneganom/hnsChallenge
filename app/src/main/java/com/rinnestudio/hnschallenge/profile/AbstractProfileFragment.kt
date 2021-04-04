package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.rinnestudio.hnschallenge.R
import com.rinnestudio.hnschallenge.utils.ImageUtils

abstract class AbstractProfileFragment : Fragment() {
    protected lateinit var profile: Profile

    private lateinit var usernameTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var subscriptionTextView: TextView
    private lateinit var subscribersTextView: TextView
    private lateinit var profilePhotoImageView: ImageView
    private lateinit var mapFragment: FragmentContainerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        configureClickListeners()
        gettingProfile()
    }

    private fun initView() {
        val view = requireView()
        usernameTextView = view.findViewById(R.id.profileUserNameTextView)
        scoreTextView = view.findViewById(R.id.profileScoreTextView)
        subscriptionTextView = view.findViewById(R.id.profileSubscriptionsTextView)
        subscribersTextView = view.findViewById(R.id.profileSubscribersTextView)
        subscribersTextView = view.findViewById(R.id.profileSubscribersTextView)
        profilePhotoImageView = view.findViewById(R.id.profilePhotoImageView)
        mapFragment = view.findViewById(R.id.profileMapFragment)
    }

    private fun configureClickListeners(){
        requireView().findViewById<TextView>(R.id.profileSubscriptionsTitleTextView).apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscriptionList()
            }
        }

        subscriptionTextView.setOnClickListener {
            navigateToSubscriptionList()
        }

        requireView().findViewById<TextView>(R.id.profileSubscribersTitleTextView).apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscribersList()
            }
        }
        subscribersTextView.setOnClickListener {
            navigateToSubscribersList()
        }

    }

    protected fun updateUI() {
        usernameTextView.text = profile.userName
        scoreTextView.text = profile.score.toString()
        subscribersTextView.text = profile.subscribers.size.toString()
        subscriptionTextView.text = profile.subscription.size.toString()
        Log.i("Log_tag", "${profile.photoReference}")
        ImageUtils().uploadProfilePhotoIntoImageView(profile.id,
            profile.photoReference,
            profilePhotoImageView)
    }


    protected abstract fun navigateToSubscribersList()
    protected abstract fun navigateToSubscriptionList()
    protected abstract fun gettingProfile()

    protected fun hideMapFragment() {
        mapFragment.visibility = View.INVISIBLE
    }

}