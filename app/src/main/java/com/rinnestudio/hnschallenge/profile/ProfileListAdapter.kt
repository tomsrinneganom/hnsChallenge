package com.rinnestudio.hnschallenge.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rinnestudio.hnschallenge.utils.ImageUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rinnestudio.hnschallenge.MainSearchFragmentDirections
import com.rinnestudio.hnschallenge.R

open class ProfileListAdapter(private val profileList: ArrayList<Profile>) :
    RecyclerView.Adapter<ProfileListAdapter.UserListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.profile_list_item, parent, false)
        return UserListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(profileList[position])
        holder.itemView.setOnClickListener {
            navigateToProfile(profileList[position], it)
        }
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    protected open fun navigateToProfile(profile: Profile, view: View) {
        if (profile.id == Firebase.auth.uid) {
            view.findNavController().navigate(R.id.ownProfileNavigationItem)
        } else {
            val navDirections =
                MainSearchFragmentDirections.actionMainSearchNavigationItemToOtherProfileFragment(
                    profile
                )
            view.findNavController().navigate(navDirections)
        }
    }


    class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profilePhotoImageView = itemView.findViewById<ImageView>(R.id.userListItemPhotoImageView)
        private val userNameTextView = itemView.findViewById<TextView>(R.id.userListItemNameTextView)
        private val userScoreTextView = itemView.findViewById<TextView>(R.id.userItemListScoreTextView)
        fun bind(profile: Profile) {
            userNameTextView.text = profile.userName
            userScoreTextView.text = profile.score.toString()
            ImageUtils().uploadProfilePhotoIntoImageView(
                profile.id,
                profile.photoReference,
                profilePhotoImageView)
        }
    }
}