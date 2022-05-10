package com.example.expirydatetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expirydatetracker.R
import com.example.expirydatetracker.database.AppDatabase
import com.example.expirydatetracker.fragments.MainActivity
import com.example.expirydatetracker.fragments.WishlistFragment
import com.example.expirydatetracker.models.UserProductWishlist


class WishlistAdapter (val context: Context, var allData: MutableList<UserProductWishlist>, var fragmentWishlist: WishlistFragment) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>(){
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val manufactuerName: TextView
        val productName: TextView
        val quantity: TextView
        val buttonDelete: Button
        init{
            manufactuerName = view.findViewById(R.id.twWishManufacturer)
            productName = view.findViewById(R.id.twWishProduct)
            quantity = view.findViewById(R.id.twWishQuantity)
            buttonDelete = view.findViewById(R.id.btnWishlistDelete)
        }
    }

    private lateinit var fragment: WishlistFragment

    init{
        fragment = fragmentWishlist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.wishlist_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current: UserProductWishlist = allData[position]
        holder.manufactuerName.text = "Произведува: " + current.product.manufacturer.name
        holder.productName.text = current.product.name
        holder.quantity.text = current.quantity.toString()
        holder.buttonDelete.setOnClickListener{
            AppDatabase.deleteWishlistItem(position, MainActivity.username!!, current.product.productId, MainActivity.auth_code!!, fragment)
        }
    }

    override fun getItemCount(): Int {
        return allData.size
    }
}