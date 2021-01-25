package com.dedstudio.hnschallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mapbox.mapboxsdk.Mapbox
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val PROFILE_NAVIGATION_NAME = "ownProfile"
    private val MAP_NAVIGATION_NAME = "mainMap"
    private val ADDING_CHALLENGE_NAVIGATION_NAME = "createChallenge"
    private val CHALLENGE_LIST_NAVIGATION_NAME = "challengeList"
    private val MAIN_SEARCH_NAVIGATION_NAME = "mainSearch"

    private lateinit var itemName: String
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        //Init mapbox
        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))

        supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment

        setUpBottomNavigation()

        imageView8.setOnClickListener {
            Firebase.auth.signOut()
            navController.navigate(R.id.signInFragment)
        }
    }

    //TODO() test
    override fun onResume() {
        super.onResume()
//        Firebase.auth.addAuthStateListener {
//            if (it.uid.isNullOrEmpty()) {
//                Firebase.auth.signOut()
//                navController.navigate(R.id.signInFragment)
//            }
//        }
    }

    private fun setUpBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        navController = findNavController(R.id.main_nav_host)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            itemName = it.title.toString()
            when (itemName) {
                PROFILE_NAVIGATION_NAME -> {
                    navigate(R.id.ownProfileNavigationItem)
                }
                MAP_NAVIGATION_NAME -> {
                    navigate(R.id.mainMapNavigationItem)
                }
                ADDING_CHALLENGE_NAVIGATION_NAME -> {
                    navigate(R.id.createChallengeNavigationItem)
                }
                CHALLENGE_LIST_NAVIGATION_NAME -> {
                    navigate(R.id.challengeListNavigationItem)
                }
                MAIN_SEARCH_NAVIGATION_NAME ->
                    navigate(R.id.mainSearchNavigationItem)
            }
            true
        }
    }

    private fun navigate(rId: Int) {
        Log.i(
            "Log_tag",
            "currentDestination: ${navController.currentDestination?.id} itemName: $itemName"
        )
        if (navController.currentDestination?.label != itemName) {
            Log.i(
                "Log_tag",
                "currentDestination: ${navController.currentDestination?.label} != itemName: $itemName"
            )
            navController.navigate(rId)
        } else {
            Log.i(
                "Log_tag",
                "currentDestination: ${navController.currentDestination?.label} == itemName: $itemName"
            )

        }
    }

}