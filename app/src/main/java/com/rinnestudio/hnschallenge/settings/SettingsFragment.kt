package com.rinnestudio.hnschallenge.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.R
import com.rinnestudio.hnschallenge.SettingsManager
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var editProfileTv: TextView
    private lateinit var logOutTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)
        editProfileTv = view.findViewById(R.id.editProfileTextView)
        logOutTv = view.findViewById(R.id.logOutTextView)
        logOutTv.apply {
            isClickable = true
            setOnClickListener {
                Firebase.auth.signOut()
                findNavController().navigate(R.id.signInFragment)
            }
        }

        val settingsManager = SettingsManager()
        view.findViewById<SwitchMaterial>(R.id.nightThemeSwitchView).apply {
            isChecked = settingsManager.getCurrentAppTheme(requireContext())
            setOnClickListener {
                viewLifecycleOwner.lifecycle.coroutineScope.launch {
                    if (isChecked) {
                        settingsManager.setDarkTheme(requireContext())
                    } else {
                        settingsManager.setLightTheme(requireContext())
                    }
                }
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

}