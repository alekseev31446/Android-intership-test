package com.example.myapplication1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val db = MainDb.getDb(view.context)
        db.getDao().getAllUsers().asLiveData().observe(viewLifecycleOwner){
            recyclerView.adapter = RecyclerViewAdapter(view.context, it.toMutableList(), requireActivity().supportFragmentManager)
        }

        return view
    }
    fun updateRecyclerViewWithSortingOption(sortingOption: Int) {
        val db = MainDb.getDb(requireContext())

        when (sortingOption) {
            0 -> {
                db.getDao().sortByName().asLiveData().observe(viewLifecycleOwner) {
                    recyclerView.adapter = RecyclerViewAdapter(view?.context!!, it.toMutableList(), requireActivity().supportFragmentManager)
                }
            }
            1 -> {
                db.getDao().sortByAge().asLiveData().observe(viewLifecycleOwner) {
                    recyclerView.adapter = RecyclerViewAdapter(view?.context!!, it.toMutableList(), requireActivity().supportFragmentManager)
                }
            }
            2 -> {
                db.getDao().sortByStatus().asLiveData().observe(viewLifecycleOwner) {
                    recyclerView.adapter = RecyclerViewAdapter(view?.context!!, it.toMutableList(), requireActivity().supportFragmentManager)
                }
            }
        }
    }
}