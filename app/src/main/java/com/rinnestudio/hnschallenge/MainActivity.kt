package com.rinnestudio.hnschallenge

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
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
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var settingsImageView: ImageView
    private var previousFragmentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))

        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        ThemeManager().setCurrentTheme(applicationContext)

        supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment

        settingsImageView = findViewById(R.id.settingsImageView)
        settingsImageView.apply {
            isClickable = true
            setOnClickListener {
                navController.navigate(R.id.settingsFragment)
            }
        }

        navController = findNavController(R.id.main_nav_host)

        setUpBottomNavigation()

        setUpNavController()
    }

    private fun setUpNavController() {
        if (Firebase.auth.uid.isNullOrEmpty()) {
            Firebase.auth.signOut()
            navController.navigate(R.id.signInFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.signInFragment || destination.id == R.id.signUpFragment) {

                bottomNavigationView.menu.forEach {
                    it.isEnabled = false
                }
                settingsImageView.isClickable = false

            } else if (previousFragmentId == R.id.signInFragment || previousFragmentId == R.id.signUpFragment) {

                bottomNavigationView.menu.forEach {
                    it.isEnabled = true
                }
                settingsImageView.isClickable = true

            }
            previousFragmentId = destination.id
        }

    }

    private fun setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.mainBottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
    }
}