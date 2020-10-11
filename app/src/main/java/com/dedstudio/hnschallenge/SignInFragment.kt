package com.dedstudio.hnschallenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sign_in_fragment, container, false)
        auth = Firebase.auth
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.mainMapNavigationItem)
        }
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), options)
        val signInButton: SignInButton = view.findViewById(R.id.sign_in_button_google)
        signInButton.setOnClickListener {
            signIn()
        }
        return view
    }

    fun signIn() {
        val intent: Intent = googleSignInClient.signInIntent
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Log_tag", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w("Log_tag", "Google sign in failed", e)

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Log_tag", "signInWithCredential:success")
                    val user = auth.currentUser
                    if (user != null) {
                        getProfile(user)
                    }
                } else {
                    Log.w("Log_tag", "signInWithCredential:failure", task.exception)
                    Snackbar.make(requireView(), "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }

            }
    }

    private fun getProfile(user: FirebaseUser) {
        Firebase.firestore.collection("userList").document(user.uid).get().addOnSuccessListener {
            Log.i("Log_tag", "get profile success")
            if (it.data == null) {
                signUp(user)
                Log.i("Log_tag", "get profile null")
            }
        }.addOnFailureListener {
            Log.i("Log_tag", "get profile failure exception:${it}")
        }
    }

    private fun signUp(user: FirebaseUser) {
        val profile = Profile(user.displayName, user.uid, user.photoUrl.toString())
        Firebase.firestore.collection("userList").document(user.uid).set(profile)
            .addOnSuccessListener {
                Log.i("Log_tag", "sign up success")
            }.addOnFailureListener {
                Log.i("Log_tag", "sign up failure exception:${it}")

            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}