package com.example.expirydatetracker.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.expirydatetracker.R
import com.example.expirydatetracker.api.EDTApi
import com.example.expirydatetracker.api.EDTApiClient
import com.example.expirydatetracker.database.AppDatabase
import com.example.expirydatetracker.transferObjects.AddTransfer
import java.time.LocalDate

class AddExpiryFragment : Fragment() {

    private lateinit var buttonConfirm: Button
    private lateinit var barcode: TextView
    private lateinit var date: DatePicker

    companion object{
        public lateinit var contextOf: Context
        public var goBackTimes = 2
    }

    private val api: EDTApi = EDTApiClient.getApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonConfirm = requireView().findViewById(R.id.btn_ae_confirm)
        barcode = requireView().findViewById(R.id.tw_ae_Barcode)
        date = requireView().findViewById(R.id.input_ae_Date)
        date.minDate = System.currentTimeMillis()
        barcode.setText(AddTransfer.scannedCode)
        buttonConfirm.setOnClickListener{
            contextOf = requireContext()
            var month = (date.month + 1).toString()
            var day  = date.dayOfMonth.toString()
            if(month.length != 2)
                month = "0"+month
            if(day.length != 2)
                day = "0"+day
            var dateString : String = date.year.toString()+"-"+month+"-"+day
            AppDatabase.addExpiry(MainActivity.username!!, barcode.text.toString(), dateString,  MainActivity.auth_code!!, this)
        }
    }

    fun notifyOnSuccess(){
        this.findNavController().navigateUp()
        if(goBackTimes == 2){
            this.findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_expiry, container, false)
    }
}