package com.example.newbagshop

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_logging2.*

class Logging : AppCompatActivity() {

    private var Email: EditText?  = null
    private var Password: EditText? = null
    private var firebaseAuth:FirebaseAuth? = null
    private var prg: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging2)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        Email = findViewById(R.id.emailadress)
        Password = findViewById(R.id.lgpassword)

        firebaseAuth = FirebaseAuth.getInstance()
        prg = ProgressDialog(this)

        loginBtn.setOnClickListener {
            validate()
        }

        forgetpasswordtxt.setOnClickListener {
            startActivity(Intent(this,PasswordRe::class.java))
        }

        val register = findViewById<TextView>(R.id.registertxt)
        register.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }
    }
    fun validate()
    {
        var email:String = Email?.text.toString()
        var password:String = Password?.text.toString()
        prg?.setMessage("Please Wait...!")
        prg?.show()
        if (email.isEmpty())
        {
            //Toast.makeText(this," ",Toast.LENGTH_SHORT).show()
            emailadress.setError("Email is Required...!")
            emailadress.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailadress.setError("Please Enter Your Valid Email Address")
            emailadress.requestFocus()
        }else if (password.isEmpty())
        {
            //Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show()
            lgpassword.setError("Please Enter Your Password")
            lgpassword.requestFocus()
        }
        else if(password.length<6)
        {
            lgpassword.setError("Password must contain min 6...!")
            lgpassword.requestFocus()
        }
        else
        {
            firebaseAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    prg?.dismiss()
                    verifyemail()
                }else
                {
                    prg?.dismiss()
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun verifyemail()
    {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val vemail = firebaseUser?.isEmailVerified
        startActivity(Intent(this,SplashActivity::class.java))
        if (vemail!!)
        {
            finish()
            startActivity(Intent(this,SplashActivity::class.java))
        }
        else
        {
            Toast.makeText(this,"Please Verify Your Email...!", Toast.LENGTH_SHORT).show()
            firebaseAuth?.signOut()
        }
    }
}