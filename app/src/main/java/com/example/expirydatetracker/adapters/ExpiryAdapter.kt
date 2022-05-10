package com.example.expirydatetracker.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.Color.red
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.expirydatetracker.R
import com.example.expirydatetracker.database.AppDatabase
import com.example.expirydatetracker.fragments.ExpiryFragment
import com.example.expirydatetracker.fragments.MainActivity
import com.example.expirydatetracker.models.Manufacturer
import com.example.expirydatetracker.models.UserProductExpiry
import com.example.expirydatetracker.models.UserProductWishlist
import org.w3c.dom.Text
import java.time.LocalDate


class ExpiryAdapter (val context: Context, var allData: MutableList<UserProductExpiry>, var fragmentExpiry: ExpiryFragment) :
    RecyclerView.Adapter<ExpiryAdapter.ViewHolder>(){
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val manufactuerName: TextView
        val productName: TextView
        val expiryDate: TextView
        val isExpired: TextView
        val buttonDelete: Button
        init{
            manufactuerName = view.findViewById(R.id.twExpiryManufacturer)
            productName = view.findViewById(R.id.twExpiryProduct)
            expiryDate = view.findViewById(R.id.twExpiryDate)
            isExpired = view.findViewById(R.id.twIsExpired)
            buttonDelete = view.findViewById(R.id.btnExpiryDelete)
        }
    }

    private lateinit var fragment: ExpiryFragment

    init{
        this.fragment = fragmentExpiry
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.expiry_card, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current: UserProductExpiry = allData[position]
        holder.manufactuerName.text = "Произведува: " + current.product.manufacturer.name
        holder.productName.text = current.product.name

        val current_date: LocalDate = LocalDate.now()
        val parts : List<String> = current.expiryDate.split("-")
        val product_date: LocalDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())

        holder.expiryDate.text = parts[2] + "/" + parts[1] + "/" + parts[0]

        holder.buttonDelete.setOnClickListener{
            AppDatabase.deleteExpiry(position, current.id, MainActivity.auth_code!!, fragment)
        }


        if(product_date.isBefore(current_date) or product_date.isEqual(current_date)){
            holder.isExpired.text = "ИСТЕЧЕНО (Не конзумирај!)"
            holder.isExpired.setTextColor(fragment.resources.getColor(R.color.red))
        }

    }

    override fun getItemCount(): Int {
        return allData.size
    }
}