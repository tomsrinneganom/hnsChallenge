package com.rinnestudio.hnschallenge

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class CameraChallengeExecutionFragment : AbstractCameraFragment(), SeekBar.OnSeekBarChangeListener {

    private val viewModel: CameraChallengeExecutionViewModel by viewModels()
    private lateinit var challengeImageView: ImageView
    private lateinit var challenge: Challenge
    private lateinit var verticalSeekBar: VerticalSeekBar
    private lateinit var challengePhoto: Bitmap
    private var isCompared = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.camera_challenge_execution_fragment, container, false)
        challengeImageView = view.findViewById(R.id.imageViewCameraChallengeExecution)
        verticalSeekBar = view.findViewById(R.id.verticalSeekBar)

        setUpSeekBar()
        getChallenge()
        uploadChallengePhoto()

        return view
    }

    private fun setUpSeekBar() {
        verticalSeekBar.setOnSeekBarChangeListener(this)
        verticalSeekBar.progress = verticalSeekBar.defaultSeekBarProgress
    }

    private fun getChallenge() {
        val args: CameraChallengeExecutionFragmentArgs by navArgs()
        challenge = args.challenge

    }

    private fun uploadChallengePhoto() {
        if (!challenge.id.isNullOrEmpty() && !challenge.creatorId.isNullOrEmpty()) {
            viewModel.uploadChallengePhoto(challenge.creatorId!!, challenge.id!!)
                .observe(viewLifecycleOwner) {
                    challengePhoto = it
                    challengeImageView.setImageBitmap(challengePhoto)
                }
        }
    }

    override fun savingPhoto() {
        val photo = BitmapFactory.decodeFile(pathToPhoto)
        if (!isCompared) {
            isCompared = true
            ImageComparsion().compare(challengePhoto, photo).observe(viewLifecycleOwner) {
                isCompared = false
                if (it) {
                    viewModel.addPoints()

                    val dialog = ChallengeSuccessfulExecutionDialogFragment()
                    dialog.show(parentFragmentManager, "ChallengeSuccessfulExecutionDialogFragment")

                    findNavController().popBackStack()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(),
                        resources.getString(R.string.wrong),
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        challengeImageView.alpha = progress.toFloat() / 100
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }
}