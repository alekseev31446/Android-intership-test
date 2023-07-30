package com.example.myapplication


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment


class CountFragment : Fragment() {
    lateinit var dataPasser: OnDataPass
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_count, container, false)
        val textViewCount: TextView = view.findViewById(R.id.textViewFragmentCount)
        val countButton: Button = view.findViewById(R.id.buttonCount)

        requireActivity().findViewById<Button>(R.id.buttonGo).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.textViewCount).visibility = View.GONE

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener{
            requireActivity().findViewById<Button>(R.id.buttonGo).visibility = View.VISIBLE
            requireActivity().findViewById<TextView>(R.id.textViewCount).visibility = View.VISIBLE
            activity?.onBackPressed()
            dataPasser.onDataPass(count.toString())
        }

        countButton.setOnClickListener {
            count++
            textViewCount.text = count.toString()
        }

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count", 0)
            textViewCount.text = count.toString()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", count)
    }
}
