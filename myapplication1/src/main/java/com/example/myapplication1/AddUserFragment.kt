package com.example.myapplication1

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class AddUserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_user, container, false)
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener{
            activity?.onBackPressed()
        }

        val db = MainDb.getDb(view.context)
        val editTextName: EditText = view.findViewById(R.id.editTextName)
        val editTextAge: EditText = view.findViewById(R.id.editTextAge)
        val buttonSave: Button = view.findViewById(R.id.button)

        buttonSave.setOnClickListener {
            val user = User(null, editTextName.text.toString(), editTextAge.text.toString().toInt())

            val progressDialog = ProgressDialog(activity)
            progressDialog.setMessage("Loading...")
            progressDialog.isIndeterminate = true
            progressDialog.setCancelable(false)
            progressDialog.show()

            Thread {
                db.getDao().insertUser(user)
                Thread.sleep(1000)
                activity?.runOnUiThread {
                    progressDialog.dismiss()
                    requireActivity().onBackPressed()
                    Toast.makeText(view.context, "User added successfully", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }

        return view
    }

}