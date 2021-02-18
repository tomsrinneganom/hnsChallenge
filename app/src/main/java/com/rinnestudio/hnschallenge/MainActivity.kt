package com.rinnestudio.hnschallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mapbox.mapboxsdk.Mapbox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

       Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))

        supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        navController = findNavController(R.id.main_nav_host)
        bottomNavigationView.setupWithNavController(navController)

        if (Firebase.auth.uid.isNullOrEmpty()){
            Firebase.auth.signOut()
            navController.navigate(R.id.signInFragment)
        }
        findViewById<ImageView>(R.id.settingsImageView).setOnClickListener {
            Firebase.auth.signOut()
            navController.navigate(R.id.settingsFragment)
        }
    }
}