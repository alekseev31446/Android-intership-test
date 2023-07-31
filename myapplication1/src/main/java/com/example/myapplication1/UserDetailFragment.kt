package com.example.myapplication1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar


private const val ARG_NAME = "name"
private const val ARG_AGE = "age"
private const val ARG_IS_STUDENT = "isStudent"

class UserDetailFragment : Fragment() {
    private var name: String? = null
    private var age: Int? = null
    private var isStudent: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            age = it.getInt(ARG_AGE)
            isStudent = it.getBoolean(ARG_IS_STUDENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener{
            activity?.onBackPressed()
        }

        val textViewName: TextView = view.findViewById(R.id.textViewName1)
        val textViewAge: TextView = view.findViewById(R.id.textViewAge1)
        val textViewIsStudent: TextView = view.findViewById(R.id.textViewIsStudent)

        textViewName.text = name
        textViewAge.text = age.toString()
        textViewIsStudent.text = if (isStudent!!) "Student" else "Not student"

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, age: Int, isStudent: Boolean) =
            UserDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putInt(ARG_AGE, age)
                    putBoolean(ARG_IS_STUDENT, isStudent)
                }
            }
    }
}