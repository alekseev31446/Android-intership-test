package com.example.myapplication1

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class RecyclerViewAdapter(val context: Context, val userList: MutableList<User>, val fragmentManager: FragmentManager) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db = MainDb.getDb(context)

        val user = userList[position]
        holder.textViewName.text = user.name
        holder.textViewAge.text = "Age: ${user.age}"
        holder.switchIsStudent.isChecked = user.isStudent

        holder.switchIsStudent.setOnCheckedChangeListener { _, isChecked ->
            user.isStudent = isChecked
            Thread{
                db.getDao().updateUser(user)
            }.start()

            Toast.makeText(holder.itemView.context, "Changed status ${user.isStudent}", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val userDetailFragment = UserDetailFragment.newInstance(user.name, user.age, user.isStudent)

            fragmentTransaction.replace(R.id.container, userDetailFragment, "firstFragment")
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            fragmentTransaction.commit()
        }

        holder.itemView.setOnLongClickListener {
            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setTitle("You want to delete this User")
                .setMessage("Are you sure you want to delete this User?")
                .setPositiveButton("Yes") { dialog, which ->
                    Thread{
                        db.getDao().deleteUser(user)
                    }.start()
                    userList.removeAt(position)
                    notifyItemRemoved(position)
                    Toast.makeText(holder.itemView.context, "User deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
            true
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewName = view.findViewById<TextView>(R.id.textViewName1)
    val textViewAge = view.findViewById<TextView>(R.id.textViewAge)
    val switchIsStudent = view.findViewById<SwitchCompat>(R.id.switchIsStudent)
}
