package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.rinnestudio.hnschallenge.R

class SubscriptionsListFragment : AbstractProfileListFragment() {

    private val viewModel: SubscriptionsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.profile_list_fragment, container, false)
    }

    override fun setUpRecycler() {
        recyclerLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerAdapter = SubscriptionListAdapter(profileList)
        bindRecycler()
    }

    override fun getProfileList() {
        val args: SubscriptionsListFragmentArgs by navArgs()
        val idList = args.idList.toList()
        viewModel.getProfileList(idList).observe(this) {
            profileList = it
                setUpRecycler()
        }

    }

}
