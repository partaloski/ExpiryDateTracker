package com.example.expirydatetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expirydatetracker.R
import com.example.expirydatetracker.models.Manufacturer
import com.example.expirydatetracker.models.Product

class ManufacturersAdapter (val context: Context, var allData: MutableList<Manufacturer>) :
    RecyclerView.Adapter<ManufacturersAdapter.ViewHolder>(){
        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
            val manufactuerName: TextView
            init{
                manufactuerName = view.findViewById(R.id.twManufacturerName)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.manufacturer_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current:Manufacturer = allData[position]
        holder.manufactuerName.text = current.name
    }

    override fun getItemCount(): Int {
        return allData.size
    }
}