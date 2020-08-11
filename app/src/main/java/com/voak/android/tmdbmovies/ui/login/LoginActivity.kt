package com.voak.android.tmdbmovies.ui.login

import android.os.Bundle
import androidx.navigation.Navigation
import com.voak.android.tmdbmovies.R
import dagger.android.support.DaggerAppCompatActivity

class LoginActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

//        val navController = Navigation.findNavController(this, R.id.login_nav_host_fragment)
//        navController.navigate(R.id.login_fragment)

//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .add(
//                        R.id.container,
//                        LoginFragment()
//                    )
//                    .commit()
//        }
    }
}