package com.example.cafemenuapp

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.cafemenuapp.Database.OrderContract
import com.google.firebase.auth.FirebaseAuth

class SummaryActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    var mAdapter: CartAdapter? = null

    val LOADER = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val clearthedata: Button = findViewById(R.id.clearthedatabase)

        clearthedata.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        LoaderManager.getInstance(this).restartLoader(LOADER, null, this)

        val listView: ListView = findViewById(R.id.list)
        mAdapter = CartAdapter(this, null)
        listView.adapter = mAdapter
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf<String>(
            OrderContract.OrderEntry._ID,
            OrderContract.OrderEntry.COLUMN_NAME,
            OrderContract.OrderEntry.COLUMN_PRICE,
            OrderContract.OrderEntry.COLUMN_QUANTITY,
        )
        return CursorLoader(
            this, OrderContract.OrderEntry.CONTENT_URI,
            projection,
            null,
            null,
            null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        // Check if data is not null before using it
        if (data != null && !data.isClosed) {
            mAdapter?.swapCursor(data)
            mAdapter?.notifyDataSetChanged()  // Notify the adapter of the data set change
        } else {
            // Handle the case where data is null or closed
            mAdapter?.swapCursor(null)
        }
        }



    override fun onLoaderReset(loader: Loader<Cursor>) {
        mAdapter?.swapCursor(null)
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