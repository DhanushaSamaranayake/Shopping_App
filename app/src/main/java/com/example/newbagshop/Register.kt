package com.example.newbagshop

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {
    private var firebaseAuth:FirebaseAuth? = null
    private var prg: ProgressDialog? = null

    internal lateinit var name:String
    internal lateinit var email:String
    internal lateinit var number:String
    internal lateinit var password:String
    internal lateinit var confirmpassword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        prg = ProgressDialog(this)

        btnRegister.setOnClickListener {
            register()
        }

    }

    fun register()
    {
        name = inputName!!.text.toString()
        email= InputEmail!!.text.toString()
        number= inputNumber!!.text.toString()
        password= inputPassword.text.toString()
        confirmpassword = inputComfirmpw.text.toString()

        prg?.setMessage("Please wait...!")
        prg?.show()

        if (name.isEmpty())
        {
            //Toast.makeText(this,"Please Enter Your Full Name",Toast.LENGTH_SHORT).show()
            inputName.setError( "Please Enter Your Full Name")
            inputName.requestFocus()
        }
        else if (email.isEmpty())
        {
            //Toast.makeText(this," ",Toast.LENGTH_SHORT).show()
            InputEmail.setError( "Email is Required...!")
            InputEmail.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            InputEmail.setError( "Please Enter Your Valid Email Address")
            InputEmail.requestFocus()
        }
        else if (number.isEmpty())
        {
            // Toast.makeText(this,"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show()
            inputNumber.setError("Please Enter Your Phone Number!")
            inputNumber.requestFocus()
        }
        else if (number.length<=9)
        {
            inputNumber.setError("Number must contain 10 numbers...!")
            inputNumber.requestFocus()

        }
        else if (password.isEmpty())
        {
            //Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show()
            inputPassword.setError("Please Enter Your Password")
            inputPassword.requestFocus()
        }
        else if(password.length<6)
        {
            inputPassword.setError("Password must contain min 6...!")
            inputPassword.requestFocus()
        }
        else if(confirmpassword.length<6)
        {
            inputComfirmpw.setError("ConfirmPassword must contain min 6...!")
            inputComfirmpw.requestFocus()
        }
        else if (confirmpassword != password)
        {
            inputComfirmpw.setError("ConfirmPassword not matched with your Password please try again")
            inputComfirmpw.requestFocus()
        }
        else if (confirmpassword.isEmpty())
        {
            //Toast.makeText(this,"Please Enter Your ConfirmPassword",Toast.LENGTH_SHORT).show()
            inputComfirmpw.setError("Please Enter Your ConfirmPassword")
            inputComfirmpw.requestFocus()
        }
        else
        {
            firebaseAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    prg?.dismiss()
                    sendData()
                    checkEmail()
                }
                else
                {
                    prg?.dismiss()
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun checkEmail()
    {
        val firebaseUser: FirebaseUser? = firebaseAuth?.currentUser
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener{ task ->
            if (task.isSuccessful)
            {
                Toast.makeText(this,"Verification Mail Sent...!", Toast.LENGTH_SHORT).show()
                firebaseAuth?.signOut()
                finish()
                startActivity(Intent(this,Logging::class.java))
            }
            else
            {
                Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun sendData()
    {
        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myReference: DatabaseReference = firebaseDatabase.getReference(firebaseAuth?.uid.toString())
        val userProfile = UserProfile(name,email,number)
        myReference.setValue(userProfile)
    }
}