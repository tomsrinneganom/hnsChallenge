package com.rinnestudio.hnschallenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var buttonSignInWithEmail: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.sign_in_fragment, container, false)

        auth = Firebase.auth
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.mainMapNavigationItem)
        }

        val googleSignInButton: SignInButton = view.findViewById(R.id.sign_in_button_google)
        googleSignInButton.setOnClickListener {
            startGoogleSignInIntent()
        }

        emailEditText = view.findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = view.findViewById(R.id.editTextTextPassword)
        buttonSignInWithEmail = view.findViewById<Button>(R.id.button).apply {
            setOnClickListener {
                signInWithEmail()
            }
        }

        view.findViewById<Button>(R.id.buttonSignInSignUp).setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        return view
    }

    private fun initGoogleClient() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), options)

        val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (lastSignedInAccount != null) {
            signInWithGoogleAccount(lastSignedInAccount.idToken!!)
        }
    }

    private fun signInWithEmail() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewLifecycleOwner.lifecycle.coroutineScope.launch {
                val signIn = viewModel.signInWithEmail(email, password)
                if (signIn) {
                    findNavController().navigate(R.id.mainMapNavigationItem)
                } else {
                    Toast.makeText(requireContext(), "Failed sign in", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun startGoogleSignInIntent() {
        initGoogleClient()

        val intent: Intent = googleSignInClient.signInIntent
        startActivityForResult(intent, 12)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Log_tag", "signInWithGoogleAccount:" + account.id)
                if (account.idToken != null) {
                    signInWithGoogleAccount(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.w("Log_tag", "Google sign in failed", e)

            }
        }
    }

    private fun signInWithGoogleAccount(idToken: String) {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            if (viewModel.signInWithGoogle(idToken)) {
                findNavController().navigate(R.id.mainMapNavigationItem)
            }
        }
    }
}
