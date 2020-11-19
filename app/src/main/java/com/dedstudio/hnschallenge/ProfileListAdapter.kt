package com.dedstudio.hnschallenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_list_item.view.*

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
        private val profilePhotoImageView = itemView.userListItemPhotoImageView
        private val userNameTextView = itemView.userListItemNameTextView
        private val userScoreTextView = itemView.userItemListScoreTextView
        fun bind(profile: Profile) {
            userNameTextView.text = profile.userName
            userScoreTextView.text = profile.score.toString()
            val photoReference = ProfileUtils().getProfilePhotoReference(profile.id!!)
            Glide.with(itemView.context).load(photoReference)
                .into(profilePhotoImageView)
        }
    }
}