package com.rinnestudio.hnschallenge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.view.updateLayoutParams
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rinnestudio.hnschallenge.utils.ChallengeUtils
import com.rinnestudio.hnschallenge.utils.ImageUtils
import kotlin.random.Random

open class ChallengeListAdapter(
    protected val challenges: List<Challenge>,
    protected val context: Context,
) :
    RecyclerView.Adapter<ChallengeListAdapter.ChallengeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.challenge_list_item, parent, false)
        return ChallengeListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        holder.bind(challenges[position], context)
        holder.challengePhoto.apply {
            setOnClickListener {
                it.findNavController()
                    .navigate(createNavDirecetionsToChallenge(challenges[position]))
            }
        }
        holder.creatorProfilePhoto.setOnClickListener {
            it.findNavController()
                .navigate(createNavDirectionsToProfile(challenges[position].creatorId!!))
        }
        holder.creatorNameTextView.setOnClickListener {
            it.findNavController()
                .navigate(createNavDirectionsToProfile(challenges[position].creatorId!!))
        }
    }

    open fun createNavDirectionsToProfile(creatorId: String): NavDirections {
        return if (creatorId == Firebase.auth.uid) {
            ChallengeListFragmentDirections.actionChallengeListNavigationItemToOwnProfileNavigationItem()
        } else {
            ChallengeListFragmentDirections.actionChallengeListNavigationItemToOtherProfileFragment(
                null, creatorId)
        }
    }

    open fun createNavDirecetionsToChallenge(challenge: Challenge): NavDirections {
        return ChallengeListFragmentDirections.actionChallengeListNavigationItemToMapChallengeExecutionFragment(
            challenge
        )
    }

    override fun getItemCount() = challenges.size

    class ChallengeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val challengePhoto: ImageView =
            itemView.findViewById(R.id.image_view_challenge_item_image)
        val creatorNameTextView: TextView =
            itemView.findViewById(R.id.text_view_challenge_item_creator_name)
        val creatorProfilePhoto: ImageView =
            itemView.findViewById(R.id.image_view_challenge_item_creator_photo)
        val item: CardView = itemView.findViewById(R.id.card_view_challenge_item)

        fun bind(challenge: Challenge, context: Context) {
            //TODO()
            val percent: Int = Random.nextInt(5, 100)
            val cardView = itemView.findViewById<CardView>(R.id.view)
            val textView = itemView.findViewById<TextView>(R.id.textView2)
            itemView.findViewById<TextView>(R.id.distanceToCurrentLocationChallengeItem).text =
                "${challenge.distanceToCurrentLocation?.toInt()} m"
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
            bindChallengePhoto(challenge, context)
            Log.i("Log_tag", "creatorName: ${challenge.creatorName}")

            bindCreator(challenge.creatorName!!,
                challenge.creatorId!!,
                challenge.creatorProfilePhotoReference)
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun bindChallengePhoto(challenge: Challenge, context: Context) {

            var isChallengePhotoLongPressed = false
            val challengePhotoReference =
                Firebase.storage.reference.child("${challenge.creatorId}/${challenge.id}.jpg")
            Glide.with(itemView).load(challengePhotoReference).into(challengePhoto)
            challengePhoto.isLongClickable = true
            val dialog = createDialog(context,challenge)

            challengePhoto.setOnLongClickListener {
                Log.i("Log_tag", "OnLongClickListener")
                dialog.show()
                isChallengePhotoLongPressed = true
                true
            }
            challengePhoto.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (isChallengePhotoLongPressed) {
                        Log.i("Log_tag", "OnLongClickListener == false")
                        dialog.cancel()
                        isChallengePhotoLongPressed = false
                    }
                }
                false
            }
        }

        private fun createDialog(context: Context, challenge: Challenge): AlertDialog {
            val dialog =
                MaterialAlertDialogBuilder(context).setView(R.layout.display_image_fragment).create()
            dialog.setOnShowListener {
                val imageView = dialog.findViewById<ImageView>(R.id.imageView2)!!
                val challengePhotoReference = ChallengeUtils().generateChallengePhotoReference(
                    challenge.creatorId!!,
                    challenge.id!!)
                Glide.with(imageView).load(challengePhotoReference).into(imageView)

            }
            return dialog
        }

        private fun bindCreator(
            creatorName: String,
            creatorId: String,
            creatorProfilePhotoReference: String?,
        ) {
            ImageUtils().uploadProfilePhotoIntoImageView(creatorId,
                creatorProfilePhotoReference,
                creatorProfilePhoto)

            creatorNameTextView.apply {
                text = creatorName
                isClickable = true
            }
            creatorProfilePhoto.apply {
                isClickable = true
            }

        }

    }

}