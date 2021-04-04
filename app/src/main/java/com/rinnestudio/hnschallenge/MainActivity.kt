package com.rinnestudio.hnschallenge

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mapbox.mapboxsdk.Mapbox
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        lifecycle.coroutineScope.launch {
            SettingsManager().setCurrentTheme(applicationContext)
        }

        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))

        supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = findNavController(R.id.main_nav_host)

        setUpBottomNavigation()

        if (Firebase.auth.uid.isNullOrEmpty()) {
            Firebase.auth.signOut()
            navController.navigate(R.id.signInFragment)
        }

        findViewById<ImageView>(R.id.settingsImageView).setOnClickListener {
            navController.navigate(R.id.settingsFragment)
        }
    }

    private fun setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.mainBottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.ownProfileNavigationItem -> {
                    navigateToReselectedItem(R.id.ownProfileNavigationItem)
                }
                R.id.mainSearchNavigationItem -> {
                    navigateToReselectedItem(R.id.mainSearchNavigationItem)
                }
                R.id.mainMapNavigationItem -> {
                    navigateToReselectedItem(R.id.mainMapNavigationItem)
                }
                R.id.createChallengeNavigationItem -> {
                    navigateToReselectedItem(R.id.createChallengeNavigationItem)
                }
                R.id.challengeListNavigationItem -> {
                    navigateToReselectedItem(R.id.challengeListNavigationItem)
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun navigateToReselectedItem(id: Int) {
        if (navController.currentDestination?.id != id) {
            navController.navigate(id)
        }
    }

}