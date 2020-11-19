package com.dedstudio.hnschallenge

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_list_fragment.*

abstract class AbstractProfileListFragment : Fragment() {

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var recyclerAdapter: ProfileListAdapter
    protected lateinit var recyclerLayoutManager: GridLayoutManager
    protected lateinit var profileList: List<Profile>

    protected abstract fun setUpRecycler()

    protected abstract fun getProfileList()

    protected fun bindRecycler() {
        recyclerView = recyclerViewUserList.apply {
            layoutManager = recyclerLayoutManager
            setHasFixedSize(true)
            adapter = recyclerAdapter
        }
    }
}