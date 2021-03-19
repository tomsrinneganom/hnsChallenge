package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.rinnestudio.hnschallenge.profile.AbstractProfileListFragment
import com.rinnestudio.hnschallenge.profile.Profile
import kotlinx.coroutines.launch

class MainSearchFragment : AbstractProfileListFragment() {

    private val viewModel: MainSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.profile_list_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewUserList)

        searchInputLayout = view.findViewById(R.id.profileListSearchInputLayout)
        searchEditText = view.findViewById(R.id.profileListSearchEditText)

        getProfileList()
        return view
    }

    override fun setUpRecycler() {
        recyclerLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerAdapter = MainSearchAdapter(profileList as ArrayList<Profile>)
        bindRecycler(requireView())
    }

    override fun getProfileList() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            profileList = viewModel.getRecommendedListOfProfiles()
            setUpRecycler()
        }
    }


}
