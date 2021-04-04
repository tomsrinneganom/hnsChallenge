package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        buttonViewUnsubscribe = view.findViewById(R.id.profileUnsubscribeButton)
        buttonViewSubscribe = view.findViewById(R.id.profileSubscribeButton)
        buttonViewUnsubscribe.setOnClickListener {
            viewModel.subscribe(profile, false)
            updateSubscribeButton(false)
            updateUI()
        }
        buttonViewSubscribe.setOnClickListener {
            viewModel.subscribe(profile, true)
            updateSubscribeButton(true)
            updateUI()
        }
        return view
    }


    //TODO()
    override fun gettingProfile() {
        val args: OtherProfileFragmentArgs by navArgs()
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            when {
                args.profile != null -> {
                    profile = args.profile!!
                }
                args.id != null -> {
                    profile = viewModel.getProfileById(args.id!!)
                }
                else -> {
                    findNavController().navigateUp()
                }
            }
            initMap()
            updateUI()
            checkSubscribe()
        }
    }

    private fun initMap() {
        mapViewModel.profileId.value = profile.id
    }

    override fun navigateToSubscribersList() {
        val subscribersIdList = profile.subscribers
        if (subscribersIdList.isNotEmpty()) {
            val navDirections =
                OtherProfileFragmentDirections.actionOtherProfileFragmentToSubscriptionsListFragment(
                    subscribersIdList.toTypedArray()
                )
            hideMapFragment()
            findNavController().navigate(navDirections)
        }
    }

    override fun navigateToSubscriptionList() {
        val subscriptionsIdList = profile.subscription
        if (subscriptionsIdList.isNotEmpty()) {
            val navDirections =
                OtherProfileFragmentDirections.actionOtherProfileFragmentToSubscriptionsListFragment(
                    subscriptionsIdList.toTypedArray()
                )
            hideMapFragment()
            findNavController().navigate(navDirections)
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
        Log.i("Log_tag", "$subscribe")
        if (subscribe) {
            buttonViewSubscribe.visibility = View.INVISIBLE
            buttonViewUnsubscribe.visibility = View.VISIBLE
        } else {
            buttonViewSubscribe.visibility = View.VISIBLE
            buttonViewUnsubscribe.visibility = View.INVISIBLE
        }
    }


}