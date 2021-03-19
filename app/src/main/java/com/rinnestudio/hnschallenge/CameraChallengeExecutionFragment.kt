package com.rinnestudio.hnschallenge

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch

class CameraChallengeExecutionFragment : AbstractCameraFragment(), SeekBar.OnSeekBarChangeListener {

    private val viewModel: CameraChallengeExecutionViewModel by viewModels()
    private lateinit var challengeImageView: ImageView
    private lateinit var challenge: Challenge
    private lateinit var verticalSeekBar: VerticalSeekBar
    private lateinit var challengePhoto: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.camera_challenge_execution_fragment, container, false)
        layout = view as ConstraintLayout

        val args: CameraChallengeExecutionFragmentArgs by navArgs()
        challenge = args.challenge
        buttonTakePhoto = view.findViewById(R.id.imageViewCameraChallengeExecutionTakePhoto)
        challengeImageView = view.findViewById(R.id.imageViewCameraChallengeExecution)
        verticalSeekBar = view.findViewById(R.id.verticalSeekBar)
        verticalSeekBar.setOnSeekBarChangeListener(this)
        verticalSeekBar.progress = 50

        if (!challenge.id.isNullOrEmpty() && !challenge.creatorId.isNullOrEmpty()) {
            Log.i("Log_tag", "challenge.id != null && challenge.profileId != null")
            viewLifecycleOwner.lifecycle.coroutineScope.launch {
                challengePhoto =
                    viewModel.uploadChallengePhoto(challenge.creatorId!!, challenge.id!!)
                challengeImageView.setImageBitmap(challengePhoto)
            }
        } else {
            Log.i("Log_tag", "challenge.id == null && challenge.profileId == null")

        }
        cameraPreviewView = view.findViewById(R.id.cameraViewChallengeExecution)
        return view
    }

    override fun savingPhoto() {
        val photo = BitmapFactory.decodeFile(pathToPhoto)
        val r = ImageComparsion().compare(challengePhoto, photo)
        Toast.makeText(requireContext(), r, Toast.LENGTH_LONG).show()
     val f = AlertDialog.Builder(requireContext()).setMessage(r).create().show()
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