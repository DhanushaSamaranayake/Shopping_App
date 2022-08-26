package com.example.newbagshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        Handler().postDelayed(Runnable {

            startActivity(Intent(this@SplashActivity,Catergory_menu::class.java))
            finish()
        },2000)
    }
}