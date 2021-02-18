package com.rinnestudio.hnschallenge

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.size
import androidx.core.view.updateLayoutParams
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rinnestudio.hnschallenge.utils.ImageUtils
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.random.Random

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
                    ChallengeListFragmentDirections.actionChallengeListNavigationItemToMapChallengeExecutionFragment(
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
        private val creatorProfilePhoto: ImageView =
            itemView.findViewById(R.id.image_view_challenge_item_creator_photo)
        val item: CardView = itemView.findViewById(R.id.card_view_challenge_item)

        fun bind(challenge: Challenge) {
            //TODO()
            val percent:Int = Random.nextInt(5,100)
            val cardView = itemView.findViewById<CardView>(R.id.view)
            val textView = itemView.findViewById<TextView>(R.id.textView2)
            cardView.updateLayoutParams {
                width = width / 2 + width / 200 * percent
            }
            when (percent) {
                in 0..100 / 3 -> {
                    cardView.setCardBackgroundColor(Color.RED)
                    textView.setTextColor(Color.RED)
                }
                in 100 / 3..100 / 2 -> {
                    cardView.setCardBackgroundColor(Color.YELLOW)
                    textView.setTextColor(Color.YELLOW)
                }
                in 100 / 2..100 -> {
                    textView.setTextColor(Color.GREEN)
                    cardView.setCardBackgroundColor(Color.GREEN)
                }
            }
            textView.text = "$percent%"

            creatorNameTextView.text = challenge.creatorName
            Log.i("Log_tag", "creatorName: ${challenge.creatorName}")
            val challengePhotoReference =
                Firebase.storage.reference.child("${challenge.creatorId}/${challenge.id}.jpg")
            Glide.with(itemView).load(challengePhotoReference).into(imageView)

            ImageUtils().uploadProfilePhotoIntoImageView(challenge.creatorId!!,
                challenge.creatorProfilePhotoReference,
                creatorProfilePhoto)

        }
    }

}