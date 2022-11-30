package ru.lehandr.framehistoryrussia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        initNavigation()
    }

    private fun initNavigation() {
        val bnvNavigationMenu = findViewById<BottomNavigationView>(R.id.bnv_navigation_menu)
        bnvNavigationMenu.selectedItemId = R.id.epochsFragment
        bnvNavigationMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.epochsFragment -> {
                    bnvNavigationMenu.menu.getItem(0).isChecked = true
                    navController.navigate(R.id.epochsFragment)
                    true
                }
                R.id.datesFragment -> {
                    bnvNavigationMenu.menu.getItem(1).isChecked = true
                    navController.navigate(R.id.datesFragment)
                    true
                }
                R.id.personsFragment -> {
                    bnvNavigationMenu.menu.getItem(2).isChecked = true
                    navController.navigate(R.id.personsFragment)
                    true
                }
                R.id.settingsFragment -> {
                    bnvNavigationMenu.menu.getItem(3).isChecked = true
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}