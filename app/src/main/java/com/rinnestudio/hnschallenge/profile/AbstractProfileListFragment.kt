package com.rinnestudio.hnschallenge.profile

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rinnestudio.hnschallenge.R

abstract class AbstractProfileListFragment : Fragment() {

    protected lateinit var profileList: List<Profile>
    private lateinit var recyclerView: RecyclerView
    protected lateinit var recyclerAdapter: AbstractProfileListAdapter
    protected lateinit var recyclerLayoutManager: GridLayoutManager
    private lateinit var searchEditText: TextInputEditText
    private lateinit var searchInputLayout: TextInputLayout
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var isSearched = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindBackPressedCallback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewUserList)
        searchInputLayout = view.findViewById(R.id.profileListSearchInputLayout)
        searchEditText = view.findViewById(R.id.profileListSearchEditText)

        getProfileList()
        bindSearchListener()
    }

    private fun bindSearchListener() {
        searchInputLayout.setEndIconOnClickListener {
            searchForProfiles()
        }
        searchEditText.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchForProfiles()
            }
            false
        }
    }


    private fun searchForProfiles() {
        val username = searchEditText.text.toString()
        if (username.isBlank()) {
            Log.i("Log_tag", "username.isBlank()")
            showAllProfiles()
        } else if (!profileList.isNullOrEmpty()) {
            val foundProfiles = mutableListOf<Profile>()

            profileList.forEach {
                if (it.userName != null) {
                    if (it.userName.equals(username)) {
                        foundProfiles.add(it)
                    }
                }

            }

            profileList.forEach {
                if (it.userName != null) {
                    if (it.userName!!.contains(username, true)) {
                        foundProfiles.add(it)
                    }
                }
            }
            showFoundProfiles(foundProfiles)
        }


    }

    protected fun bindRecycler() {
        recyclerView.apply {
            layoutManager = recyclerLayoutManager
            setHasFixedSize(true)
            adapter = recyclerAdapter
        }
    }

    private fun showFoundProfiles(profileList: List<Profile>) {
        recyclerAdapter.removeAllItems()
        recyclerAdapter.addItems(profileList)
        isSearched = true
    }

    private fun showAllProfiles() {

        recyclerAdapter.removeAllItems()
        recyclerAdapter.addItems(profileList)
        isSearched = false
    }

    private fun bindBackPressedCallback() {
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback {
            if (isSearched) {
                showAllProfiles()
            } else {
                this.isEnabled = false
                this.remove()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()

    }


    protected abstract fun setUpRecycler()
    protected abstract fun getProfileList()

}