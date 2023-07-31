package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = "Level 2"


        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val listFragment = ListFragment()

        fragmentTransaction.replace(R.id.container, listFragment, "listFragment")
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_user -> {
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val addUserFragment = AddUserFragment()

                fragmentTransaction.replace(R.id.container, addUserFragment, "addUserFragment")
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                fragmentTransaction.commit()
                true
            }
            R.id.action_sorting -> {
                SortingDialogFragment().show(supportFragmentManager, "SortingDialogFragment")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
