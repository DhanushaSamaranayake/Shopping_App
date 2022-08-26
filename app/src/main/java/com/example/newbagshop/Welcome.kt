package com.example.newbagshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class Welcome : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val welcome_btn = findViewById<Button>(R.id.welcome_btn)
        welcome_btn.setOnClickListener {
            val intent = Intent(this,Logging::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        val user: FirebaseUser? = firebaseAuth?.currentUser
        if (user!=null)
        {
            startActivity(Intent(this,Home::class.java))
        }
        else
        {
            startActivity(Intent(this,Logging::class.java))
        }




    }
}