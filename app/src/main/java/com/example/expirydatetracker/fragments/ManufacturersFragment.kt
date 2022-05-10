package com.example.expirydatetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expirydatetracker.R
import com.example.expirydatetracker.adapters.ManufacturersAdapter
import com.example.expirydatetracker.database.AppDatabase

class ManufacturersFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var rvAdapter: ManufacturersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerManufacturers)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        rvAdapter = ManufacturersAdapter(requireActivity().applicationContext, AppDatabase.manufacturers.data)
        recyclerView.adapter = rvAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manufacturers2, container, false)
    }
}