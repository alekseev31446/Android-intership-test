package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), OnDataPass {
    private var countValue: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonGo: Button = findViewById(R.id.buttonGo)
        buttonGo.setOnClickListener {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val countFragment = CountFragment()

            fragmentTransaction.replace(R.id.container, countFragment, "countFragment")
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            fragmentTransaction.commit()
            openOptionsMenu()
        }
    }

    override fun onDataPass(data: String) {
        countValue = data
        val textViewCount: TextView = findViewById(R.id.textViewCount)
        textViewCount.text = countValue
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("countValue", countValue)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        countValue = savedInstanceState.getString("countValue", "0")
        val textViewCount: TextView = findViewById(R.id.textViewCount)
        textViewCount.text = countValue
    }
}