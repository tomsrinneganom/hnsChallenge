package com.rinnestudio.hnschallenge.profile

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rinnestudio.hnschallenge.R

abstract class AbstractProfileListFragment : Fragment() {

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var recyclerAdapter: ProfileListAdapter
    protected lateinit var recyclerLayoutManager: GridLayoutManager
    protected lateinit var profileList: List<Profile>

    protected abstract fun setUpRecycler()

    protected abstract fun getProfileList()

    protected fun bindRecycler(view:View) {
        recyclerView =  view.findViewById<RecyclerView>(R.id.recyclerViewUserList).apply {
            layoutManager = recyclerLayoutManager
            setHasFixedSize(true)
            adapter = recyclerAdapter
        }
    }
}