package com.example.newbagshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbagshop.models.Menus
import com.example.newbagshop.R

class PlaceyourAdapter(val menuList:List<Menus?>?):RecyclerView.Adapter<PlaceyourAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceyourAdapter.MyViewHolder {
       val view:View = LayoutInflater.from(parent.context).inflate(R.layout.placeyourorder_list_row,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceyourAdapter.MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (menuList == null ) 0 else menuList.size
    }
    inner class MyViewHolder(view:View):RecyclerView.ViewHolder(view)
    {
        val thumbImage:ImageView = view.findViewById(R.id.thumbImage)
        val menuName:TextView = view.findViewById(R.id.menuName)
        val menuPrice:TextView = view.findViewById(R.id.menuPrice)
        val menuQty:TextView = view.findViewById(R.id.menuQty)

        fun bind(menus: Menus)
        {

            menuName.text = menus?.name
            menuPrice.text = "Price $ " + String.format("%.2f",menus?.price!! * menus.totalInCart)
            menuQty.text = "Qty : " +menus?.totalInCart

            Glide.with(thumbImage)
                .load(menus?.url)
                .into(thumbImage)
        }
    }
}