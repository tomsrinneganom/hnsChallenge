package com.rinnestudio.hnschallenge

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private val googleSignInResult = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                if (account.idToken != null) {
                    signInWithGoogleAccount(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.w("Log_tag", "Google sign in failed", e)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.sign_in_fragment, container, false)

        auth = Firebase.auth
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.mainMapNavigationItem)
        }

        view.findViewById<SignInButton>(R.id.signInGoogleButton).apply {
            setOnClickListener {
                signInWithGoogleAccount()
            }
        }
        view.findViewById<Button>(R.id.signInWithEmailButton).apply {
            setOnClickListener {
                signInWithEmail()
            }
        }
        view.findViewById<Button>(R.id.signUpButton).setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        emailEditText = view.findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = view.findViewById(R.id.editTextTextPassword)

        bindBackPressedCallback()

        return view
    }
    private fun bindBackPressedCallback() {
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback{

        }
    }

    private fun signInWithEmail() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotBlank() && password.isNotBlank()) {
            viewModel.signInWithEmail(email, password).observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(R.id.mainMapNavigationItem)
                } else {
                    Toast.makeText(requireContext(), "Failed sign in", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    private fun signInWithGoogleAccount() {

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), options)

        val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (lastSignedInAccount != null && lastSignedInAccount.idToken != null) {
            signInWithGoogleAccount(lastSignedInAccount.idToken!!).observe(viewLifecycleOwner) {
                if (!it) {
                    googleSignInResult.launch(googleSignInClient.signInIntent)
                }
            }
        } else
            googleSignInResult.launch(googleSignInClient.signInIntent)

    }

    private fun signInWithGoogleAccount(idToken: String): LiveData<Boolean> {
        val signInResult = viewModel.signInWithGoogle(idToken)
        signInResult.observe(viewLifecycleOwner) {
            Log.i("Log_tag", "$it")
            if (it) {
                findNavController().navigate(R.id.mainMapNavigationItem)
            }
        }
        return signInResult
    }
    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()

    }
}
