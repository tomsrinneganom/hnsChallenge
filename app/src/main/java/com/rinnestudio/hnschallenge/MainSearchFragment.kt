package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.rinnestudio.hnschallenge.profile.AbstractProfileListFragment

class MainSearchFragment : AbstractProfileListFragment() {

    private val viewModel: MainSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.profile_list_fragment, container, false)
    }

    override fun setUpRecycler() {
        recyclerLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerAdapter = MainSearchAdapter(profileList)
        bindRecycler()
    }

    override fun getProfileList() {
        viewModel.getRecommendedListOfProfiles().observe(this) {
            profileList = it
            setUpRecycler()
        }
    }


}
