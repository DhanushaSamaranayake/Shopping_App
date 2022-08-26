package com.example.newbagshop.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbagshop.models.ShoppingModel
import com.example.newbagshop.R

class ShopListAdapter(val shoppingList: List<ShoppingModel?>?, val clickListener: ShoppingListClickListener ): RecyclerView.Adapter<ShopListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopListAdapter.MyViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.recycle_shopping_list_row,parent,false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListAdapter.MyViewHolder, position: Int) {
        holder.bind(shoppingList?.get(position))
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(shoppingList?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return shoppingList?.size!!
    }

    inner class MyViewHolder(View:View):RecyclerView.ViewHolder(View)
    {
        val thumbImage: ImageView = View.findViewById(R.id.thumbImage)
        val tvshopname:TextView = View.findViewById(R.id.tvshopname)
        fun bind(shoppingModel: ShoppingModel?)
        {
            tvshopname.text = shoppingModel?.name

            Glide.with(thumbImage)
                .load(shoppingModel?.image)
                .into(thumbImage)
        }
    }

    interface ShoppingListClickListener
    {

        fun onItemClick(shoppingModel: ShoppingModel?)
    }
}