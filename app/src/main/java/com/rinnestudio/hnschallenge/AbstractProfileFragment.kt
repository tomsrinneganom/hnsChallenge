package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.rinnestudio.hnschallenge.utils.ImageUtils

abstract class AbstractProfileFragment : Fragment() {

    protected lateinit var profile: Profile
    private lateinit var usernameTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var subscriptionTextView: TextView
    private lateinit var subscribersTextView: TextView
    private lateinit var profilePhotoImageView: ImageView
    private lateinit var mapFragment: FragmentContainerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        gettingProfile()
        setUpView()
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

    private fun setUpView() {
        requireView().findViewById<TextView>(R.id.profileSubscriptionsTitleTextView).apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscriptionList()
            }
        }
        requireView().findViewById<TextView>(R.id.profileSubscribersTitleTextView).apply {
            isClickable = true
            setOnClickListener {
                navigateToSubscribersList()
            }
        }

        subscriptionTextView.setOnClickListener {
            navigateToSubscriptionList()
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
        ImageUtils().uploadProfilePhotoIntoImageView(profile.id,
            profile.photoReference,
            profilePhotoImageView)
    }
    
    protected fun navigate(navDirections: NavDirections) {
        mapFragment.visibility = View.INVISIBLE
        findNavController().navigate(navDirections)
    }

    protected abstract fun navigateToSubscribersList()
    protected abstract fun navigateToSubscriptionList()
    protected abstract fun gettingProfile()


}