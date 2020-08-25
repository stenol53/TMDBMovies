package com.voak.android.tmdbmovies.ui.bottomnavigation

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.voak.android.tmdbmovies.BaseActivity
import com.voak.android.tmdbmovies.R
import dagger.android.support.DaggerAppCompatActivity

class BottomNavigationActivity : BaseActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_bottom_navigation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bottom_navigation)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.fragment_container)
        navView.setupWithNavController(navController)

        supportActionBar?.hide()
    }
}