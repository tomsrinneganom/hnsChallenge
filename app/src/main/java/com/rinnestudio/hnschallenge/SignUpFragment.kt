package com.rinnestudio.hnschallenge

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by activityViewModels()
    private lateinit var profilePhotoImageView: ImageView
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var dialog: AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)

        profilePhotoImageView = view.findViewById(R.id.imageView10)
        initChoosePhoto()

        emailEditText = view.findViewById(R.id.editTextSignUpEmail)
        usernameEditText = view.findViewById(R.id.editTextSignUpUsername)
        passwordEditText = view.findViewById(R.id.editTextSignUpPassword)

        view.findViewById<Button>(R.id.buttonSignUp).setOnClickListener {
            signUp()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.profilePhoto.observeForever {
            profilePhotoImageView.setImageBitmap(it)
        }
    }

    private fun signUp() {
        val email = emailEditText.text.toString()
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
            viewLifecycleOwner.lifecycle.coroutineScope.launch {
                if (viewModel.signUp(email, username, password)){
                    findNavController().navigate(R.id.mainMapNavigationItem)
                }
            }
        }
    }

    private fun initChoosePhoto() {
        dialog =
            AlertDialog.Builder(requireActivity()).setTitle("Select Profile Photo").setItems(
                arrayOf("Gallery", "Camera")
            ) { _, which ->
                if (which == 0) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "image/*"
                    }
                    startActivityForResult(intent, 1)
                } else {

                }
            }.create()

        profilePhotoImageView.apply {
            isClickable = true
            setOnClickListener {
                dialog.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Log.i("Log_tag", "requestCode == 1 && resultCode == Activity.RESULT_OK")
            val photoUri = data?.data
            if (photoUri != null) {
                val navDirections =
                    SignUpFragmentDirections.actionSignUpFragmentToCropProfilePhotoFragment(photoUri.toString())
                findNavController().navigate(navDirections)
            }
        }
    }

}