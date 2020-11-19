package com.dedstudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager

class MainSearchFragment : AbstractProfileListFragment() {

    private val viewModel: MainSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //TODO() edit layout to main_search_fragment
        val view = inflater.inflate(R.layout.profile_list_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewUserList)
        getProfileList()
        return view
    }

    override fun setUpRecycler() {
        recyclerLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerAdapter = ProfileListAdapter(profileList as ArrayList<Profile>)
        bindRecycler()
    }

    override fun getProfileList() {
        viewModel.getProfileList().observe(viewLifecycleOwner) {
            profileList = it
            setUpRecycler()
        }
    }

}