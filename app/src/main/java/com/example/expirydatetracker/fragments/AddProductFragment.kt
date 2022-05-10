package com.example.expirydatetracker.fragments

import ManufListAdapter
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.expirydatetracker.R
import com.example.expirydatetracker.database.AppDatabase
import com.example.expirydatetracker.models.Manufacturer
import com.example.expirydatetracker.transferObjects.AddTransfer
import java.util.*
import kotlin.collections.ArrayList

class AddProductFragment : Fragment(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var barcode: TextView
    private lateinit var spinner: Spinner
    private lateinit var nameProduct: EditText
    private lateinit var button: Button

    private var itemSelected: Int = 0
    private var itemWasSelected: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barcode = requireView().findViewById(R.id.tw_add_Barcode)
        spinner = requireView().findViewById(R.id.spin_add_ProductManufacturer)
        nameProduct = requireView().findViewById(R.id.et_add_ProductName)
        button = requireView().findViewById(R.id.btn_add_ConfirmAdd)

        contextOf = requireContext()

        spinner.setOnItemSelectedListener(this);

        barcode.setText(AddTransfer.scannedCode)

        var manuflistAdapter : ManufListAdapter = ManufListAdapter(requireContext(), AppDatabase.manufacturers.data)
        spinner.adapter = manuflistAdapter

        button.setOnClickListener{
            if(itemWasSelected && nameProduct.text.toString().isNotEmpty()){
                AppDatabase.addProduct(barcode.text.toString(), nameProduct.text.toString(), itemSelected, this)
            }
            else{
                Toast.makeText(requireContext(), "Немате селектирано производител.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object{
        public lateinit var contextOf: Context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        itemSelected=AppDatabase.manufacturers.data[position].id
        itemWasSelected = true
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        itemWasSelected = false
    }
}