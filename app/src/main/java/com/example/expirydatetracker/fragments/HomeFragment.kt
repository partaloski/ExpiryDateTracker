package com.example.expirydatetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.expirydatetracker.R
import com.example.expirydatetracker.database.AppDatabase
import com.example.expirydatetracker.databinding.FragmentSecondBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppDatabase.refreshData()
        AppDatabase.refreshUserData()
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.twLoggedInAs.setText("Најавен/а како: " + MainActivity.username!!)

        binding.buttonToProductsNew.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_productsFragment)
        }
        binding.buttonToManufacturersNew.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_manufacturersFragment)
        }
        binding.buttonToWishlistNew.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_wishlistFragment)
        }
        binding.buttonToExpiriesNew.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_expiryFragment)
        }
        binding.buttonToBarcodeScannerNew.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_barcodeFragment)
        }
        binding.buttonToAddManufacturersNew.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_addManufacturerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}