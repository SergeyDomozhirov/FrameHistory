package ru.lehandr.framehistoryrussia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import ru.lehandr.domain.model.EpochsModel
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val auth = Firebase.auth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController: NavController
    private val RC_SIGN_IN = 555
    private val TAG = "KAN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        initNavigation()
        //authLogin()
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

    private fun authLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        /*loginBtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d(TAG, "111")
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
                }
            }
    }

}