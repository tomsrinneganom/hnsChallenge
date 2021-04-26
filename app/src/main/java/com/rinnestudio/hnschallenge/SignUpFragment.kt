package com.rinnestudio.hnschallenge

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by activityViewModels()
    private lateinit var profilePhotoImageView: ImageView
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    private val cropPhotoResult = registerForActivityResult(GetContent()) {
        val photoUri = it
        if (photoUri != null) {
            val navDirections =
                SignUpFragmentDirections.actionSignUpFragmentToCropProfilePhotoFragment(photoUri.toString())
            findNavController().navigate(navDirections)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)

        emailEditText = view.findViewById(R.id.emailEditText)
        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        profilePhotoImageView = view.findViewById(R.id.selectProfilePhotoButton)
        view.findViewById<Button>(R.id.signUpButton).setOnClickListener {
            signUp()
        }

        bindChoosePhoto()

        viewModel.profilePhoto.observe(viewLifecycleOwner) {
            profilePhotoImageView.setImageBitmap(it)
        }


        return view
    }

    private fun bindChoosePhoto() {
        val dialog =
            AlertDialog.Builder(requireActivity()).setTitle("Select Profile Photo").setItems(
                arrayOf("Gallery")
            ) { _, which ->
                if (which == 0) {
                    cropPhotoResult.launch("image/*")
                }
            }.create()

        profilePhotoImageView.apply {
            isClickable = true
            setOnClickListener {
                dialog.show()
            }
        }
    }

    private fun signUp() {
        val email = emailEditText.text.toString()
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
            viewModel.signUp(email, username, password).observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(R.id.mainMapNavigationItem)
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}