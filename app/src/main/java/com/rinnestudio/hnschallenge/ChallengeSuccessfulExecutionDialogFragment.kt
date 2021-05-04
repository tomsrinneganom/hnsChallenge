package com.rinnestudio.hnschallenge

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton

class ChallengeSuccessfulExecutionDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.challenge_successful_execution_dialog, null)
            builder.setView(view)

            val button = view.findViewById<MaterialButton>(R.id.acceptButtonView)
            button.setOnClickListener {
                dismiss()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }
}