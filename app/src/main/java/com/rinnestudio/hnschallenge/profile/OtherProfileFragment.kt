package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherProfileFragment : AbstractProfileFragment() {

    private val viewModel: OtherProfileViewModel by viewModels()
    private val mapViewModel: OtherProfileMapViewModel by viewModels()
    private lateinit var buttonViewSubscribe: MaterialButton
    private lateinit var buttonViewUnsubscribe: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.other_profile_fragment, container, false)
        initSubscriptionButtons(view)
        return view
    }

    private fun initSubscriptionButtons(view: View) {
        buttonViewUnsubscribe = view.findViewById(R.id.profileUnsubscribeButton)
        buttonViewUnsubscribe.setOnClickListener {
            viewModel.unSubscribe().observe(viewLifecycleOwner) {
                if (it) {
                    updateSubscribeButton(false)
                    updateUI()
                }
            }
        }

        buttonViewSubscribe = view.findViewById(R.id.profileSubscribeButton)
        buttonViewSubscribe.setOnClickListener {
            viewModel.subscribe().observe(viewLifecycleOwner) {
                if (it) {
                    updateSubscribeButton(true)
                    updateUI()
                }
            }
        }
    }

    override fun gettingProfile() {
        val args: OtherProfileFragmentArgs by navArgs()
        when {
            args.profile != null -> {
                profile = args.profile!!
                viewModel.setProfile(profile)
                setProfileValues()
            }
            args.id != null -> {
                viewModel.getProfile(args.id!!).observe(this) {
                    profile = it
                    setProfileValues()
                }
            }
            else -> {
                findNavController().navigateUp()
            }
        }
    }

    private fun setProfileValues() {
        mapViewModel.profileId.value = profile.id
        updateUI()
        checkSubscribe()

    }

    override fun navigateToSubscribersList() {
        val subscribersIdList = profile.subscribers
        if (subscribersIdList.isNotEmpty()) {
            val navDirections =
                OtherProfileFragmentDirections.actionOtherProfileFragmentToSubscriptionsListFragment(
                    subscribersIdList.toTypedArray()
                )
            navigate(navDirections)
        }
    }

    override fun navigateToSubscriptionList() {
        val subscriptionsIdList = profile.subscription
        if (subscriptionsIdList.isNotEmpty()) {
            val navDirections =
                OtherProfileFragmentDirections.actionOtherProfileFragmentToSubscriptionsListFragment(
                    subscriptionsIdList.toTypedArray()
                )
            navigate(navDirections)
        }
    }

    private fun checkSubscribe() {
        val ownId = Firebase.auth.uid
        if (profile.subscribers.contains(ownId)) {
            updateSubscribeButton(true)
        } else {
            updateSubscribeButton(false)
        }
    }


    private fun updateSubscribeButton(subscribe: Boolean) {
        if (subscribe) {
            buttonViewSubscribe.visibility = View.INVISIBLE
            buttonViewUnsubscribe.visibility = View.VISIBLE
        } else {
            buttonViewSubscribe.visibility = View.VISIBLE
            buttonViewUnsubscribe.visibility = View.INVISIBLE
        }
    }


}