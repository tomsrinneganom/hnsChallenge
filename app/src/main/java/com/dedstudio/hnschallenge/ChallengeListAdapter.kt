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
        public val item: CardView = itemView.findViewById(R.id.card_view_challenge_item)

        fun bind(challenge: Challenge) {
//            creatorNameTextView.text = challenge.creatorName
            creatorNameTextView.text = "Denissssssss chechel"
            val reference =
                Firebase.storage.reference.child("${challenge.creatorId}/${challenge.id}.jpg")

            val file = File.createTempFile("images", "jpg")

            reference.getFile(file).addOnSuccessListener {
                Log.i("Log_tag", "SUCCESSS get challenge image1")
                val bitmap = BitmapFactory.decodeFile(file.path)
                Log.i("Log_tag", "SUCCESSS get challenge image1 file path ${file.path}")

                imageView.setImageBitmap(bitmap)
            }.addOnFailureListener { exception ->
                Log.i("Log_tag", "exception get image${exception.message}")
            }

            Glide.with(itemView)
                .load(challenge.creatorPhoto)
                .into(creatorPhoto)

        }
    }

}