package com.example.myapplication2

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class RecyclerViewAdapter(val context: Context, val list: MutableList<Animal>, val layoutId: Int) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(layoutId, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = list[position]
        val file = File(animal.imageFilePath)
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)

        holder.imageView.setImageBitmap(bitmap)
        if(animal.isLiked){
            holder.imageButton.setImageResource(R.drawable.star_on)
        }else{
            holder.imageButton.setImageResource(R.drawable.star_off)
        }
        holder.imageButton.setOnClickListener {
            if(animal.isLiked){
                holder.imageButton.setImageResource(R.drawable.star_off)
                animal.isLiked = false
            }else{
                holder.imageButton.setImageResource(R.drawable.star_on)
                animal.isLiked = true
            }
            val db = MainDb.getDb(context)
            Thread{
                db.getDao().updateAnimal(animal)
            }.start()
        }
    }
    fun addData(newData: List<Animal>) {
        list.addAll(newData)
        notifyDataSetChanged()
    }
    fun updateData(newData: List<Animal>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView = view.findViewById<ImageView>(R.id.imageView)
    val imageButton = view.findViewById<ImageButton>(R.id.imageButton)
}
