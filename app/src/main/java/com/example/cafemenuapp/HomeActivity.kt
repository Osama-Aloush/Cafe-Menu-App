package com.example.cafemenuapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {
    var foodList: List<Food>? = null


    var mAdapter: FoodsAdapter? = null

   // var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "Menu Cafe"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Database Helper
        val database = DatabaseHelper(applicationContext)
        val foodList = database.getFoodList(applicationContext)

        val recyclerView = findViewById<RecyclerView>(R.id.list_item_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter = FoodsAdapter(foodList)
        recyclerView.adapter = mAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}