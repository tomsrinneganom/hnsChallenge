package com.dedstudio.hnschallenge

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_list_item.view.*

class UserListAdapter(private val profiles: ArrayList<Profile>) :
    RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return UserListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(profiles[position])
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profilePhotoImageView = itemView.userListItemPhotoImageView
        private val userNameTextView = itemView.userListItemNameTextView
        fun bind(profile: Profile) {
            userNameTextView.text = profile.userName
            Glide.with(itemView).load(profile.profilePhotoReference).into(profilePhotoImageView)
            itemView.setOnClickListener{
                Log.i("Log_tag","click")
            }
        }
    }
}