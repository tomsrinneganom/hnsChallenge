package com.rinnestudio.hnschallenge

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.updateLayoutParams
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rinnestudio.hnschallenge.utils.ImageUtils
import kotlin.random.Random

abstract class AbstractChallengeListAdapter(
    protected var challenges: MutableList<Challenge>,
    protected val context: Context,
) : RecyclerView.Adapter<AbstractChallengeListAdapter.ChallengeListViewHolder>() {
    var deletePosition:Int = 22222
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        Log.i("Log_tag", "onCreateViewHolder()")
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.challenge_list_item, parent, false)
        return ChallengeListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        Log.i("Log_tag", "position: $position")

        if(position != deletePosition) {
            holder.bind(challenges[position])

            holder.challengePhotoImageView.apply {
                isClickable = true
                setOnClickListener {
                    it.findNavController()
                        .navigate(createNavDirecetionsToChallenge(challenges[position]))
                }
            }

            holder.creatorProfilePhotoImageView.apply {
                isClickable = true
                setOnClickListener {
                    it.findNavController()
                        .navigate(createNavDirectionsToProfile(challenges[position].creatorId!!))
                }
            }
            holder.creatorNameTextView.apply {
                isClickable = true
                setOnClickListener {
                    it.findNavController()
                        .navigate(createNavDirectionsToProfile(challenges[position].creatorId!!))
                }
            }
        }
    }

    override fun getItemCount() = challenges.size

    fun deleteChallenge(challenge: Challenge){
        val index = challenges.indexOf(challenge)
        deletePosition = index
        Log.i("Log_tag", "index +$index")
        challenges.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index,challenges.size)
    }

    abstract fun createNavDirectionsToProfile(creatorId: String): NavDirections

    abstract fun createNavDirecetionsToChallenge(challenge: Challenge): NavDirections


    class ChallengeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val challengePhotoImageView: ImageView =
            itemView.findViewById(R.id.image_view_challenge_item_image)

        val creatorNameTextView: TextView =
            itemView.findViewById(R.id.text_view_challenge_item_creator_name)

        val creatorProfilePhotoImageView: ImageView =
            itemView.findViewById(R.id.image_view_challenge_item_creator_photo)

        fun bind(challenge: Challenge) {
            itemView.findViewById<TextView>(R.id.distanceToCurrentLocationChallengeItem).text =
                "${challenge.distanceToCurrentLocation?.toInt()} m"

            initDifficultyScale()
            bindChallengePhoto(challenge)
            bindCreator(challenge.creatorName!!,
                challenge.creatorId!!,
                challenge.creatorProfilePhotoReference)
        }

        private fun initDifficultyScale() {
            val difficultyScaleCardView =
                itemView.findViewById<CardView>(R.id.difficultyScaleCardView)
            val difficultyScaleTextView =
                itemView.findViewById<TextView>(R.id.difficultyScaleTextView)

            val percent: Int = Random.nextInt(5, 100)

            difficultyScaleCardView.updateLayoutParams {
                width = width / 2 + width / 200 * percent
            }

            when (percent) {
                in 0..100 / 3 -> {
                    difficultyScaleCardView.setCardBackgroundColor(Color.RED)
                    difficultyScaleTextView.setTextColor(Color.RED)
                }
                in 100 / 3..100 / 2 -> {
                    difficultyScaleCardView.setCardBackgroundColor(Color.YELLOW)
                    difficultyScaleTextView.setTextColor(Color.YELLOW)
                }
                in 100 / 2..100 -> {
                    difficultyScaleTextView.setTextColor(Color.GREEN)
                    difficultyScaleCardView.setCardBackgroundColor(Color.GREEN)
                }
            }

            difficultyScaleTextView.text = "$percent%"

        }

        private fun bindChallengePhoto(challenge: Challenge) {
            val challengePhotoReference =
                Firebase.storage.reference.child("${challenge.creatorId}/${challenge.id}.jpg")

            Glide.with(itemView).load(challengePhotoReference).into(challengePhotoImageView)

        }

        private fun bindCreator(
            creatorName: String,
            creatorId: String,
            creatorProfilePhotoReference: String?,
        ) {
            ImageUtils().uploadProfilePhotoIntoImageView(creatorId,
                creatorProfilePhotoReference,
                creatorProfilePhotoImageView)

            creatorNameTextView.text = creatorName
        }

    }

}