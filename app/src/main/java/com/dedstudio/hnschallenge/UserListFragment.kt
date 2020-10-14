package com.dedstudio.hnschallenge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.user_list_fragment.*

class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModels()

    private lateinit var recyclerView:RecyclerView
    private lateinit var recyclerAdapter: UserListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.user_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val idList = mutableListOf<String>()
        idList.add(Firebase.auth.uid!!)
        viewModel.getProfileList(idList).observe(viewLifecycleOwner){
            Log.i("Log_tag", "observe")

            recyclerAdapter = UserListAdapter(it as ArrayList<Profile>)
            recyclerView = recyclerViewUserList.apply {
                layoutManager = GridLayoutManager(requireContext(),2)
                setHasFixedSize(true)
                adapter = recyclerAdapter
            }
            Log.i("Log_tag", "itemcount ${recyclerAdapter.itemCount}")
        }


    }

}