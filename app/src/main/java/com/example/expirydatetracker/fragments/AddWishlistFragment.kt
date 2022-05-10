package com.example.expirydatetracker.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.expirydatetracker.R
import com.example.expirydatetracker.api.EDTApi
import com.example.expirydatetracker.api.EDTApiClient
import com.example.expirydatetracker.database.AppDatabase
import com.example.expirydatetracker.transferObjects.AddTransfer

class AddWishlistFragment : Fragment() {

    private lateinit var buttonConfirm: Button
    private lateinit var barcode: TextView
    private lateinit var quantity: EditText

    companion object{
        public lateinit var contextOf: Context
        public var goBackTimes = 2
    }


    private val api: EDTApi = EDTApiClient.getApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonConfirm = requireView().findViewById(R.id.btn_aw_confirm)
        barcode = requireView().findViewById(R.id.tw_aw_Barcode)
        quantity = requireView().findViewById(R.id.input_aw_Quantity)
        barcode.setText(AddTransfer.scannedCode)
        buttonConfirm.setOnClickListener{
            AddExpiryFragment.contextOf = requireContext()
            AppDatabase.addWishlistItem(MainActivity.username!!, barcode.text.toString(), quantity.text.toString().toInt(), MainActivity.auth_code!!, this)
        }
    }

    fun notifyOnSuccess(){
        this.findNavController().navigateUp()
        if(AddExpiryFragment.goBackTimes == 2){
            this.findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_wishlist, container, false)
    }
}