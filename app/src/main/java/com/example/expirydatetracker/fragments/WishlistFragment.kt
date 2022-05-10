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
import com.example.expirydatetracker.adapters.WishlistAdapter
import com.example.expirydatetracker.database.AppDatabase
import org.w3c.dom.Text

class WishlistFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rvAdapter: WishlistAdapter
    private lateinit var noItemsTW: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    public fun invalidateAdapter(position: Int){
        rvAdapter.notifyItemRemoved(position)
        if(AppDatabase.wishlist.data.size == 0){
            recyclerView.visibility = RecyclerView.INVISIBLE
            noItemsTW.visibility = TextView.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noItemsTW = requireView().findViewById(R.id.tw_wl_noItems)

        recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerWishlist)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        rvAdapter = WishlistAdapter(requireActivity().applicationContext, AppDatabase.wishlist.data, this)
        recyclerView.adapter = rvAdapter
        if(AppDatabase.wishlist.data.size == 0){
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
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }
}