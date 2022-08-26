package com.example.newbagshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_password_re.*


class PasswordRe : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_re)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        Resetbtn.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        var emailR = resetemail.text.toString()
        if(emailR.isEmpty())
        {
            Toast.makeText(this,"Please Enter Your Email", Toast.LENGTH_SHORT).show()
        }
        firebaseAuth?.sendPasswordResetEmail(emailR)?.addOnCompleteListener{
                task ->
            if (task.isSuccessful)
            {
                Toast.makeText(this,"Reset Email Sent", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,Logging::class.java))
            }
            else
            {
                Toast.makeText(this,"Reset Email not Sent", Toast.LENGTH_SHORT).show()
            }
        }
    }

}