package com.example.newbagshop


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.newbagshop.models.ShoppingModel
import com.example.newbagshop.R
import kotlinx.android.synthetic.main.activity_succes_order.*

class Succes_order : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_succes_order)

       // val shoppingModel: ShoppingModel? = intent.getParcelableExtra("ShoppingModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()


        buttonDone.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}