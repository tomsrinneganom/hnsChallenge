package com.dedstudio.hnschallenge

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

open class ChallengeListAdapter(protected val challenges: List<Challenge>) :
    RecyclerView.Adapter<ChallengeListAdapter.ChallengeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.challenge_list_item, parent, false)
        return ChallengeListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        holder.bind(challenges[position])
        holder.item.apply {
            isClickable = true
            setOnClickListener {
                val navDirections =
                    ChallengeListFragmentDirections.actionChallengeLsitNavigationItemToMapChallengeExecutionFragment(
                        challenges[position]
                    )
                it.findNavController().navigate(navDirections)
            }
        }
    }

    override fun getItemCount() = challenges.size

    class ChallengeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView =
            itemView.findViewById(R.id.image_view_challenge_item_image)
        private val creatorNameTextView: TextView =
            itemView.findViewById(R.id.text_view_challenge_item_creator_name)
        private val creatorPhoto: ImageView =
            itemView.findViewById(R.id.image_view_challenge_item_creator_photo)
        val item: CardView = itemView.findViewById(R.id.card_view_challenge_item)

        fun bind(challenge: Challenge) {
            creatorNameTextView.text = challenge.creatorName
            Log.i("Log_tag", "creatorName: ${challenge.creatorName}")
            val reference =
                Firebase.storage.reference.child("${challenge.creatorId}/${challenge.id}.jpg")
            val profilePhotoReference =
                ProfileUtils().getProfilePhotoReference(challenge.creatorId!!)

            Glide.with(itemView)
                .load(profilePhotoReference)
                .into(creatorPhoto)

            Glide.with(itemView).load(reference).into(imageView)

        }
    }

}