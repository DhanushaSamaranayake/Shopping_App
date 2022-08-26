package com.example.newbagshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newbagshop.Fragment.user_fragment
import com.example.newbagshop.models.Menus
import com.example.newbagshop.models.ShoppingModel
import com.example.newbagshop.adapter.menuListAdapter
import kotlinx.android.synthetic.main.activity_shopping_menus.*

class Shopping_menus : AppCompatActivity(), menuListAdapter.MenuListClickListener {

    private var itemsInTheCartList: MutableList<Menus?>? = null
    private var totalItemCartCount = 0
    private var menuList: List<Menus?>? = null
    private var menuListAdapter: menuListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_menus)

        val shoppingModel = intent?.getParcelableExtra<ShoppingModel>("ShoppingModel")

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(shoppingModel?.name)
        actionBar?.setDisplayHomeAsUpEnabled(true)


        menuList = shoppingModel?.menus

        initRecycleView(menuList)
        checkoutbutton.setOnClickListener {

            if (itemsInTheCartList != null && itemsInTheCartList!!.size == 0)
            {
                Toast.makeText(this@Shopping_menus,"Please Add some items into cart",Toast.LENGTH_LONG).show()
            }
            else
            {
                shoppingModel?.menus = itemsInTheCartList
                val intent = Intent(this@Shopping_menus, PlaceYourOrder::class.java)
                intent.putExtra("ShoppingModel",shoppingModel)
               startActivityForResult(intent,1000)
            }
        }
    }

    private fun initRecycleView(menus: List<Menus?>?) {
        menurecycleView.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = menuListAdapter(menus,this)
        menurecycleView.adapter = menuListAdapter
    }

    override fun addToCartClickListener(menus: Menus) {
        if (itemsInTheCartList == null) {
            itemsInTheCartList = ArrayList()
        }
        itemsInTheCartList?.add(menus)
        totalItemCartCount = 0
        for (menu in itemsInTheCartList!!) {
            totalItemCartCount = totalItemCartCount + menu?.totalInCart!!
        }
        checktxt.visibility = View.VISIBLE
        checktxt.text = "" + totalItemCartCount + ""

    }

    override fun updateCartClickListener(menus: Menus) {
        val index = itemsInTheCartList!!.indexOf(menus)
        itemsInTheCartList?.removeAt(index)
        itemsInTheCartList?.add(menus)
        totalItemCartCount = 0
        for (menu in itemsInTheCartList!!) {
            totalItemCartCount = totalItemCartCount + menu?.totalInCart!!
        }
        checktxt.visibility = View.VISIBLE
       checktxt.text = "" + totalItemCartCount + ""

    }

    override fun removeFromCartClickListener(menus: Menus) {
        if (itemsInTheCartList!!.contains(menus))
            itemsInTheCartList?.remove(menus)
        for (menu in itemsInTheCartList!!) {
            totalItemCartCount = totalItemCartCount + menu?.totalInCart!!
        }
        checktxt.visibility = View.VISIBLE
       checktxt.text = "" + totalItemCartCount + ""

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id==R.id.uprofile)
        {
            startActivity(Intent(this,Home::class.java))
            return true
        }
       when(item.itemId)
        {
            android.R.id.home -> finish()
            else ->{}
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode == RESULT_OK)
        {
           finish()
        }
   }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile, menu)
        return true
    }



}