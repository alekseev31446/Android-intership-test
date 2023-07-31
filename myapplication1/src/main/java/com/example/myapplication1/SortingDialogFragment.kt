package com.example.myapplication1

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SortingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sortingOptions = arrayOf(
            "Sort by name (Alphabetical)",
            "Sort by age (youngest first)",
            "Sort by student status (student first)"
        )

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Sorting Options")
                .setItems(sortingOptions) { _, which ->
                    val listFragment = requireActivity().supportFragmentManager.findFragmentByTag("listFragment") as? ListFragment
                    listFragment?.updateRecyclerViewWithSortingOption(which)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
