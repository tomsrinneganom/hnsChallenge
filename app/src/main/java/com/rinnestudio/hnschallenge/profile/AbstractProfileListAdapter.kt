package com.rinnestudio.hnschallenge.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rinnestudio.hnschallenge.R
import com.rinnestudio.hnschallenge.utils.ImageUtils

abstract class AbstractProfileListAdapter(profileList: ArrayList<Profile>) :
    RecyclerView.Adapter<AbstractProfileListAdapter.UserListViewHolder>() {

    private val data = mutableListOf<Profile>()
    init {
        data.addAll(profileList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.profile_list_item, parent, false)
        return UserListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        Log.i("Log_tag", "position: $position")
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            navigateToProfile(data[position], it)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun removeAllItems() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addItems(profileList: List<Profile>) {
        data.addAll(profileList)
        notifyDataSetChanged()
    }

    protected abstract fun navigateToProfile(profile: Profile, view: View)


    class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profilePhotoImageView =
            itemView.findViewById<ImageView>(R.id.userListItemPhotoImageView)
        private val userNameTextView =
            itemView.findViewById<TextView>(R.id.userListItemNameTextView)
        private val userScoreTextView =
            itemView.findViewById<TextView>(R.id.userItemListScoreTextView)

        fun bind(profile: Profile) {
            userNameTextView.text = profile.userName
            userScoreTextView.text = profile.score.toString()
            userScoreTextView.text = profile.score.toString()
            ImageUtils().uploadProfilePhotoIntoImageView(
                profile.id,
                profile.photoReference,
                profilePhotoImageView)
        }
    }
}