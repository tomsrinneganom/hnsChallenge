package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractChallengeListFragment : Fragment() {
    private lateinit var emptyListTextView: TextView
    private lateinit var recyclerView: RecyclerView
    protected lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    protected lateinit var challenges: MutableList<Challenge>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.challenge_list_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_challenge_list)
        emptyListTextView = view.findViewById(R.id.emptyListTextView)
        viewManager = GridLayoutManager(context, 2)
        getChallenges()
        return view
    }


    protected fun bindRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    protected fun displayTextForEmptyList(){
        emptyListTextView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    protected abstract fun getChallenges()

    protected abstract fun initViewAdapter()

}