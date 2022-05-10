package com.example.expirydatetracker.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.expirydatetracker.R
import com.example.expirydatetracker.database.AppDatabase
import com.example.expirydatetracker.models.Product
import com.example.expirydatetracker.transferObjects.AddTransfer

private const val CAMERA_REQUEST_CODE = 101

class BarcodeFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var barcodeScanned: TextView
    private lateinit var textViewForProduct: TextView
    private lateinit var buttonAdd: Button
    private lateinit var buttonAddExpiry: Button
    private lateinit var buttonAddWishlist: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAdd = requireView().findViewById(R.id.button_add_product)
        buttonAddExpiry = requireView().findViewById(R.id.button_add_expiry)
        buttonAddWishlist = requireView().findViewById(R.id.button_add_to_wishlist)
        textViewForProduct = requireView().findViewById(R.id.tv_scanProduct)

        buttonAdd.visibility = Button.INVISIBLE
        buttonAddExpiry.visibility = Button.INVISIBLE
        buttonAddWishlist.visibility = Button.INVISIBLE

        buttonAdd.setOnClickListener{
            AddTransfer.scannedCode = barcodeScanned.text.toString()
            findNavController().navigate(R.id.action_barcodeFragment_to_addProductFragment)
        }

        buttonAddExpiry.setOnClickListener{
            AddTransfer.scannedCode = barcodeScanned.text.toString()
            AddExpiryFragment.goBackTimes = 2
            findNavController().navigate(R.id.action_barcodeFragment_to_addExpiryFragment)
        }

        buttonAddWishlist.setOnClickListener{
            AddTransfer.scannedCode = barcodeScanned.text.toString()
            AddWishlistFragment.goBackTimes = 2
            findNavController().navigate(R.id.action_barcodeFragment_to_addWishlistFragment)
        }


        setupPermissions()
        codeScanner()
    }

    private fun codeScanner(){
        var codeScannerView : CodeScannerView = requireView().findViewById(R.id.scannerView)
        barcodeScanned = requireView().findViewById(R.id.tv_scannedCode)
        this.codeScanner = CodeScanner(requireContext(), codeScannerView)

        codeScanner.apply{
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE

            scanMode = ScanMode.CONTINUOUS

            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                requireActivity().runOnUiThread{
                    barcodeScanned.setText(it.text)
                    barcodeScanned.visibility = TextView.VISIBLE
                    val product : Product? = AppDatabase.products.contains(it.text)
                    if(product != null){
                        textViewForProduct.setText("Скениран продукт: \n" + product.manufacturer.name + " " + product.name)
                        buttonAddExpiry.visibility = Button.VISIBLE
                        buttonAddWishlist.visibility = Button.VISIBLE
                        buttonAdd.visibility = Button.INVISIBLE
                    }
                    else{
                        textViewForProduct.setText("Скенирај продукт...")
                        buttonAddExpiry.visibility = Button.INVISIBLE
                        buttonAddWishlist.visibility = Button.INVISIBLE
                        buttonAdd.visibility = Button.VISIBLE
                    }
                }
            }

            errorCallback = ErrorCallback {
                requireActivity().runOnUiThread{
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }

            codeScannerView.setOnClickListener{
                codeScanner.startPreview()
            }
        }


    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_barcode, container, false)
    }

    private fun setupPermissions(){
        val permission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireContext(), "YOU NEED THE CAMERA TO USE THIS APP", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}