package com.example.newbagshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newbagshop.models.ShoppingModel
import com.example.newbagshop.adapter.ShopListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.*
import java.lang.Exception

class Catergory_menu : AppCompatActivity(), ShopListAdapter.ShoppingListClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle("Shopping Catergory")
        val ShoppingModel = getShoppingData()
        initRecycleView(ShoppingModel)
    }

    private fun initRecycleView(shoppingList: List<ShoppingModel?>?) {
        val recycleViewShopping = findViewById<RecyclerView>(R.id.recycleViewShopping)
        recycleViewShopping.layoutManager = LinearLayoutManager(this,)
        val adapter = ShopListAdapter(shoppingList, this)
        recycleViewShopping.adapter = adapter
    }

    private fun getShoppingData(): List<ShoppingModel?>? {
        val inputStream: InputStream = resources.openRawResource(R.raw.shop)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } catch (x: Exception) {

        }
        val jsonString: String = writer.toString()
        val gson = Gson()
        val ShoppingModel =
            gson.fromJson<Array<ShoppingModel>>(jsonString, Array<ShoppingModel>::class.java)
                .toList()
        return ShoppingModel
    }

    override fun onItemClick(shoppingModel: ShoppingModel?) {
        val intent = Intent(this@Catergory_menu, Shopping_menus::class.java)
        intent.putExtra("ShoppingModel", shoppingModel)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id==R.id.uprofile)
        {
            startActivity(Intent(this,Home::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}


