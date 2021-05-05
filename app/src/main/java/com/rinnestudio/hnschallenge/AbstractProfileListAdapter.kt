package com.rinnestudio.hnschallenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.utils.ImageUtils

abstract class AbstractProfileListAdapter(profileList: List<Profile>) :
    RecyclerView.Adapter<AbstractProfileListAdapter.UserListViewHolder>() {

    private val profileList = mutableListOf<Profile>()

    init {
        this.profileList.addAll(profileList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.profile_list_item, parent, false)
        return UserListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(profileList[position])
        holder.itemView.setOnClickListener {
            if (profileList[position].id == Firebase.auth.uid) {
                navigateToOwnProfile(it)
            } else {
                navigateToOtherProfile(profileList[position], it)

            }
        }
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    fun removeAllItems() {
        profileList.clear()
        notifyDataSetChanged()
    }

    fun addItems(profileList: List<Profile>) {
        this.profileList.addAll(profileList)
        notifyDataSetChanged()
    }

    private fun navigateToOwnProfile(view: View) {
        view.findNavController().navigate(R.id.ownProfileNavigationItem)
    }

    protected abstract fun navigateToOtherProfile(profile: Profile, view: View)

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