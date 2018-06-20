package com.example.nglauber.navigationdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupActionBarWithNavController(
                this, Navigation.findNavController(this, R.id.navHost)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHost).navigateUp()
    }
}