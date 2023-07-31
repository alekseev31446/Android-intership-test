package com.example.myapplication2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LikedListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: MainDb
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = layoutManager

        db = MainDb.getDb(view.context)
        db.getDao().getLikedAnimals().observe(viewLifecycleOwner){
            adapter = RecyclerViewAdapter(view.context, it.toMutableList(), R.layout.liked_list_item)
            recyclerView.adapter = adapter
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        db.getDao().getLikedAnimals().observe(viewLifecycleOwner){
            adapter.updateData(it)
        }

    }
}
