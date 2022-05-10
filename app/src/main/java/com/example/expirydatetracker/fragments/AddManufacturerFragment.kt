package com.example.expirydatetracker.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.expirydatetracker.R
import com.example.expirydatetracker.database.AppDatabase

class AddManufacturerFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var buttonAdd: Button
    private lateinit var manufacturerName: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAdd = requireView().findViewById(R.id.btn_am_ConfirmAdd)
        manufacturerName = requireView().findViewById(R.id.et_am_ManufacturerName)

        contextOf = requireContext()

        buttonAdd.setOnClickListener{
            AppDatabase.addManufacturer(manufacturerName.text.toString(), this)
        }
    }

    companion object{
        public lateinit var contextOf: Context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_manufacturer, container, false)
    }
}