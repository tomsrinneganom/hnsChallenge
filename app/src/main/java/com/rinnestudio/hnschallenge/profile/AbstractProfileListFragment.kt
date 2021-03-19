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

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var recyclerAdapter: AbstractProfileListAdapter
    protected lateinit var recyclerLayoutManager: GridLayoutManager
    protected lateinit var profileList: List<Profile>
    protected lateinit var searchEditText: TextInputEditText
    protected lateinit var searchInputLayout: TextInputLayout
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var search = false

    protected abstract fun setUpRecycler()

    protected abstract fun getProfileList()

    protected fun bindRecycler(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewUserList).apply {
            layoutManager = recyclerLayoutManager
            setHasFixedSize(true)
            adapter = recyclerAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackPressedCallback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchListener()
    }

    private fun initSearchListener() {
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
        if (username.isEmpty()) {
            showAllProfiles()
        } else {
            val foundProfiles = mutableListOf<Profile>()
            profileList.forEach {
                if (it.userName != null) {
                    if (it.userName.equals(username)) {
                        foundProfiles.add(it)
                        Log.i("Log_tag", "equals ${it.userName}")
                        return@forEach
                    }
                }
            }
            profileList.forEach {
                if (it.userName != null) {
                    if (it.userName!!.contains(username, true)) {
                        foundProfiles.add(it)
                        Log.i("Log_tag", "contains ${it.userName}")
                    }
                }
            }
            showFoundProfiles(foundProfiles)
        }
    }

    private fun showFoundProfiles(profileList: List<Profile>) {
        recyclerAdapter.removeAllItems()
        recyclerAdapter.addItems(profileList)
        search = true
    }

    private fun showAllProfiles() {
        recyclerAdapter.removeAllItems()
        recyclerAdapter.addItems(profileList)
        search = false
    }

    private fun initBackPressedCallback() {
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback {
            if (search) {
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
}