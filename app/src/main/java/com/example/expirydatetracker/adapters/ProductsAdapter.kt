package com.example.expirydatetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.expirydatetracker.R
import com.example.expirydatetracker.fragments.ProductsFragment
import com.example.expirydatetracker.models.Product
import com.example.expirydatetracker.transferObjects.ViewTransfer

class ProductsAdapter (val context: Context, var allData: MutableList<Product>, fragment: ProductsFragment) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){
        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
            val productName: TextView
            val productManufacturer: TextView
            val productBarcode: TextView
            val openProduct: Button
            init{
                productName = view.findViewById(R.id.twProductName)
                productManufacturer = view.findViewById(R.id.twProductManufacturer)
                productBarcode = view.findViewById(R.id.twProductBarcode)
                openProduct = view.findViewById(R.id.btnProductOpen)
            }
        }

    private lateinit var fragment: ProductsFragment

    init{
       this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current:Product = allData[position]
        holder.productName.text = current.name
        holder.productManufacturer.text = "Произведува: " + current.manufacturer.name
        holder.productBarcode.text = "Бар-код: " + current.productId
        holder.openProduct.setOnClickListener{
            ViewTransfer.product = current
            fragment.findNavController().navigate(R.id.action_productsFragment_to_productViewFragment)
        }
    }

    override fun getItemCount(): Int {
        return allData.size
    }
}