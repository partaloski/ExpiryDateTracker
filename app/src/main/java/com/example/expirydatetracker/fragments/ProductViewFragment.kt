package com.example.expirydatetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.expirydatetracker.R
import com.example.expirydatetracker.models.Product
import com.example.expirydatetracker.transferObjects.AddTransfer
import com.example.expirydatetracker.transferObjects.ViewTransfer

class ProductViewFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var product: Product
    private lateinit var buttonBack: Button
    private lateinit var buttonAddExpiry: Button
    private lateinit var buttonAddWishlist: Button

    private lateinit var productName: TextView
    private lateinit var productManufacturer: TextView
    private lateinit var productBarcode: TextView

    init{
        product = ViewTransfer.product
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonBack = requireView().findViewById(R.id.btn_ProductBack)
        buttonAddExpiry = requireView().findViewById(R.id.btn_ProductAddExpiry)
        buttonAddWishlist = requireView().findViewById(R.id.btn_ProductAddWishlist)

        productName = requireView().findViewById(R.id.tw_ProductName)
        productBarcode = requireView().findViewById(R.id.tw_ProductBarcode)
        productManufacturer = requireView().findViewById(R.id.tw_ProductManufacturer)

        productName.setText(product.name)
        productBarcode.setText("Баркод: " + product.productId)
        productManufacturer.setText("Произведува: " + product.manufacturer.name)

        buttonBack.setOnClickListener{
            findNavController().navigateUp()
        }

        buttonAddWishlist.setOnClickListener{
            AddTransfer.scannedCode = product.productId
            AddWishlistFragment.goBackTimes = 1
            findNavController().navigate(R.id.action_productViewFragment_to_addWishlistFragment)
        }

        buttonAddExpiry.setOnClickListener{
            AddTransfer.scannedCode = product.productId
            AddExpiryFragment.goBackTimes = 2
            findNavController().navigate(R.id.action_productViewFragment_to_addExpiryFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_view, container, false)
    }
}