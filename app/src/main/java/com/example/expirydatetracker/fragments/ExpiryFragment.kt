package com.example.expirydatetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expirydatetracker.R
import com.example.expirydatetracker.adapters.ExpiryAdapter
import com.example.expirydatetracker.database.AppDatabase

class ExpiryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rvAdapter: ExpiryAdapter
    private lateinit var noItemsTW: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    public fun invalidateAdapter(position: Int){
        rvAdapter.notifyItemRemoved(position)
        if(AppDatabase.expiries.data.size == 0){
            recyclerView.visibility = RecyclerView.INVISIBLE
            noItemsTW.visibility = TextView.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerExpiries)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        rvAdapter = ExpiryAdapter(requireActivity().applicationContext, AppDatabase.expiries.data, this)
        noItemsTW = requireView().findViewById(R.id.tw_exp_noItems)
        recyclerView.adapter = rvAdapter

        if(AppDatabase.expiries.data.size == 0){
            recyclerView.visibility = RecyclerView.INVISIBLE
            noItemsTW.visibility = TextView.VISIBLE
        }
        else{
            recyclerView.visibility = RecyclerView.VISIBLE
            noItemsTW.visibility = TextView.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expiry, container, false)
    }
}