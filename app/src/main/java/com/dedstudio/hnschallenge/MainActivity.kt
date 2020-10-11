package com.dedstudio.hnschallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapbox.mapboxsdk.Mapbox

class MainActivity : AppCompatActivity() {
    private val PROFILE_NAVIGATION_NAME = "mainProfileNavigationItem"
    private val MAP_NAVIGATION_NAME = "mainMapNavigationItem"
    private val ADDING_CHALLENGE_NAVIGATION_NAME = "addingChallengeNavigationItem"
    private val CHALLENGE_LIST_NAVIGATION_NAME = "challengeListNavigationItem"
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
    }

    private fun setUpBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            navController = findNavController(R.id.main_nav_host)
            itemName = it.title.toString()
            when (itemName) {
                PROFILE_NAVIGATION_NAME -> {
                    navigate(R.id.mainProfileNavigationItem)
                }
                MAP_NAVIGATION_NAME -> {
                   navigate(R.id.mainMapNavigationItem)
                }
                ADDING_CHALLENGE_NAVIGATION_NAME -> {
                    navigate(R.id.addingChallengeNavigationItem)
                }
                CHALLENGE_LIST_NAVIGATION_NAME -> {
                    navigate(R.id.challengeListNavigationItem)
                }
            }
            true
        }
    }

    private fun navigate(rId: Int) {
        if(navController.currentDestination?.label != itemName){
            navController.navigate(rId)
        }
    }

}