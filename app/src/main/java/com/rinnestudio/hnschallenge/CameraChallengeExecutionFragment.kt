package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch

class CameraChallengeExecutionFragment : Camera(), SeekBar.OnSeekBarChangeListener {

    private val viewModel: CameraChallengeExecutionViewModel by viewModels()
    private lateinit var challengeImageView: ImageView
    private lateinit var challenge: Challenge
    private lateinit var verticalSeekBar: VerticalSeekBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.camera_challenge_execution_fragment, container, false)
        val args: CameraChallengeExecutionFragmentArgs by navArgs()
        challenge = args.challenge
        buttonTakePhoto = view.findViewById(R.id.imageViewCameraChallengeExecutionTakePhoto)
        challengeImageView = view.findViewById(R.id.imageViewCameraChallengeExecution)
        verticalSeekBar = view.findViewById(R.id.verticalSeekBar)
        verticalSeekBar.setOnSeekBarChangeListener(this)
        verticalSeekBar.progress = 50

        if (!challenge.id.isNullOrEmpty() && !challenge.creatorId.isNullOrEmpty()) {
            Log.i("Log_tag", "challenge.id != null && challenge.creatorId != null")
            viewLifecycleOwner.lifecycle.coroutineScope.launch {
                val photo = viewModel.uploadChallengePhoto(challenge.creatorId!!, challenge.id!!)
                challengeImageView.setImageBitmap(photo)
            }
        } else {
            Log.i("Log_tag", "challenge.id == null && challenge.creatorId == null")

        }
        cameraPreviewView = view.findViewById(R.id.cameraViewChallengeExecution)
        return view
    }

    override fun savingPhoto() {

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        challengeImageView.alpha = progress.toFloat() / 100
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.i("Log_tag", "onStartTrackingTouch()")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        Log.i("Log_tag", "onStopTrackingTouch()")

    }

}