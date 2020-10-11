package com.dedstudio.hnschallenge

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.profile_map_fragment.view.*

class ProfileMapFragment : MainMapFragment() {
    private val viewModel: ProfileMapViewModel by viewModels()

}