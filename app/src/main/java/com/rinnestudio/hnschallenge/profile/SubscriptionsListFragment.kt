package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.rinnestudio.hnschallenge.R
import kotlinx.coroutines.launch

class SubscriptionsListFragment : AbstractProfileListFragment() {
    private val viewModel: SubscriptionsListViewModel by viewModels()

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
        recyclerAdapter = SubscriptionListAdapter(profileList as ArrayList<Profile>)
        bindRecycler(requireView())
    }

    override fun getProfileList() {
        val args: SubscriptionsListFragmentArgs by navArgs()
        val idList = args.idList.toList()
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            profileList = viewModel.getProfileList(idList)
            setUpRecycler()
        }
    }

}
