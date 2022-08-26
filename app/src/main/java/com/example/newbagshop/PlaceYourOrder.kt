package com.example.newbagshop

import android.content.Intent
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbagshop.models.ShoppingModel
import com.example.newbagshop.adapter.PlaceyourAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_place_your_order.*
import org.json.JSONObject

class PlaceYourOrder : AppCompatActivity(),PaymentResultListener {

    var placeyourAdapter: PlaceyourAdapter? = null
    var isDeliveryOn:Boolean = false
    var subTotalAmount = 0f
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val shoppingModel: ShoppingModel? = intent.getParcelableExtra("ShoppingModel")
        val actionBar:ActionBar? = supportActionBar
        actionBar?.setTitle(shoppingModel?.name)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        Checkout.preload(getApplicationContext())

        buttonPlaceYourOrder.setOnClickListener {
            onPlaceOrderButtonClick(shoppingModel)
        }




        switchDelivery?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked)
            {
                inputAddress.visibility = View.VISIBLE
                inputCity.visibility = View.VISIBLE
                inputState.visibility = View.VISIBLE
                inputZip.visibility = View.VISIBLE
                tvDeliveryCharge.visibility = View.VISIBLE
                tvDeliveryChargeAmount.visibility = View.VISIBLE
                isDeliveryOn = true
                calculateTotalAmount(shoppingModel)
            }
            else
            {
                inputAddress.visibility = View.GONE
                inputCity.visibility = View.GONE
                inputState.visibility = View.GONE
                inputZip.visibility = View.GONE
                tvDeliveryCharge.visibility = View.GONE
                tvDeliveryChargeAmount.visibility = View.GONE
                isDeliveryOn = false
                calculateTotalAmount(shoppingModel)

            }
        }
        initRecycleView(shoppingModel)
        calculateTotalAmount(shoppingModel)
    }

    private fun initRecycleView(shoppingModel: ShoppingModel?)
    {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeyourAdapter = PlaceyourAdapter(shoppingModel?.menus)
        cartItemsRecyclerView.adapter = placeyourAdapter
    }

    private fun calculateTotalAmount(shoppingModel: ShoppingModel?)
    {
        for (menu in shoppingModel?.menus!!)
        {
            subTotalAmount += menu?.price!! * menu?.totalInCart!!
        }
        tvSubtotalAmount.text = "$"+ String.format("%.2f",subTotalAmount)
        if (isDeliveryOn)
        {
            tvDeliveryChargeAmount.text = "$" + String.format("%.2f",shoppingModel.delivery_charge?.toFloat())
            subTotalAmount += shoppingModel?.delivery_charge?.toFloat()!!
        }
        tvTotalAmount.text = "$" + String.format("%.2f",subTotalAmount)
    }



    private fun onPlaceOrderButtonClick(shoppingModel: ShoppingModel?)
    {
        val samount: String = java.lang.String.valueOf(subTotalAmount)
        val amount = Math.round(samount.toFloat() * 100)



        if (TextUtils.isEmpty(inputName.text.toString()))
        {
            inputName.error = "Enter Your Name"
            return
        }
        else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString()))
        {
            inputAddress.error = "Enter Your Address"
            return
        }
        else if (isDeliveryOn && TextUtils.isEmpty(inputCity.text.toString()))
        {
            inputCity.error = "Enter Your City"
            return
        }
        else if (isDeliveryOn && TextUtils.isEmpty(inputZip.text.toString()))
        {
            inputZip.error = "Enter Your Zip Code"
            return
        }

        val Name = inputName.text.toString()
        val Address = inputAddress.text.toString()
        val City = inputCity.text.toString()
        val State = inputState.text.toString()
        val Zip = inputZip.text.toString()
        val Chargers = tvDeliveryChargeAmount.text.toString()
        val Amount = tvTotalAmount.text.toString()

        database = FirebaseDatabase.getInstance().getReference("Delivery")
        val delivery = Delivery(Name,Address,City,State,Zip,Chargers,Amount)
        database.child(Name).setValue(delivery).addOnSuccessListener {
            inputName.text.clear()
            inputAddress.text.clear()
            inputCity.text.clear()
            inputState.text.clear()
            inputZip.text.clear()

            Toast.makeText(this,"Successfully Add",Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
        }

       /* else if (TextUtils.isEmpty(inputCardNumber.text.toString()))
        {
            inputCardNumber.error = "Enter Your Credit card number"
            return
        }
        else if (TextUtils.isEmpty(inputCardExpiry.text.toString()))
        {
            inputCardExpiry.error = "Enter Your Credit card Expiry date"
            return
        }
        else if (TextUtils.isEmpty(inputCardPin.text.toString()))
        {
            inputCardPin.error = "Enter Your Credit card pin/CVV"
            return
        }*/

        val co = Checkout()
        try {
            val options = JSONObject()
            options.put("name","New Bag Shop")
            options.put("description","Let's Pay Payment With Us")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3700B3");
            options.put("currency","USD");
            options.put("amount", amount)//pass amount in currency subunits

            val retryObj =  JSONObject()
             retryObj.put("enabled", true);
             retryObj.put("max_count", 4);
             options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","info@gmail.com")
            prefill.put("contact","+9477523649")

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

        val intent = Intent(this@PlaceYourOrder, Succes_order::class.java)
        intent.putExtra("ShoppingModel",shoppingModel)
        startActivityForResult(intent,1000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000){
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> finish()
            else ->{}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Sucsess: "+ p0,Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Failed: "+ p1,Toast.LENGTH_LONG).show()
    }


}


