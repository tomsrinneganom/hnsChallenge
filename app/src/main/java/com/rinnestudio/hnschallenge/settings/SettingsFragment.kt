package com.rinnestudio.hnschallenge.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.R
import com.rinnestudio.hnschallenge.ThemeManager

class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var editProfileTv: TextView
    private lateinit var logOutTv: TextView
    private lateinit var darkThemeSwitchView: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)
        initView(view)
        bindLogOut()
        bindDarkThemeSwitcher()
        return view
    }

    private fun initView(view: View) {
        editProfileTv = view.findViewById(R.id.editProfileTextView)
        logOutTv = view.findViewById(R.id.logOutTextView)
        darkThemeSwitchView = view.findViewById(R.id.darkThemeSwitchView)
    }

    private fun bindLogOut() {
        logOutTv.apply {
            isClickable = true

            setOnClickListener {
                Firebase.auth.signOut()
                findNavController().navigate(R.id.signInFragment)
            }

        }
    }

    private fun bindDarkThemeSwitcher() {
        val themeManager = ThemeManager()
        darkThemeSwitchView.apply {
            isChecked = themeManager.getCurrentAppTheme(requireContext())

            setOnClickListener {
                if (isChecked) {
                    themeManager.setDarkTheme(requireContext())
                } else {
                    themeManager.setLightTheme(requireContext())
                }
            }

        }

    }

}