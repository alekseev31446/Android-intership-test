package com.example.myapplication2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private val listViewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerViewAdapter(view.context, mutableListOf(), R.layout.list_item)
        recyclerView.adapter = adapter

        listViewModel.allAnimals.observe(viewLifecycleOwner) { animals ->
            adapter.addData(animals)
        }

        loadNextPage()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadNextPage()
                }
            }
        })

        return view
    }

    private fun loadNextPage() {
        listViewModel.loadNextPage()
    }

    override fun onResume() {
        super.onResume()
        val db = MainDb.getDb(view?.context!!)
        db.getDao().getAllAnimals().observe(viewLifecycleOwner){
            adapter.updateData(it)
        }
    }
}
