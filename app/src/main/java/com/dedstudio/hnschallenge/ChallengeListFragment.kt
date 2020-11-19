package com.dedstudio.hnschallenge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber.tag

open class ChallengeListFragment : Fragment() {

    private val viewModel: ChallengeListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    protected lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.challenge_list_fragment, container, false)
        Log.i("Log_tag", "userName = ${Firebase.auth.currentUser?.displayName}")
        recyclerView = view.findViewById(R.id.recycler_view_challenge_list)
        viewManager = GridLayoutManager(context, 2)
        getChallenges()
        return view
    }

    protected open fun getChallenges() {
        val challenges = viewModel.getChallenges()
        challenges.observe(viewLifecycleOwner) {
            initViewAdapter(it)
            bindRecyclerView()
        }
    }

    protected open fun initViewAdapter(challenges: List<Challenge>) {
        viewAdapter = ChallengeListAdapter(challenges)
    }

    protected fun bindRecyclerView() {

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }
}