package com.example.cafemenuapp

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.cafemenuapp.Database.OrderContract


class DetailActivity : FragmentActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    var addtoCart: Button? = null       //findViewById(R.id.addtocart)
    var quantitynumber: TextView? = null
    var drinkName: TextView? = null
    var coffeePrice: TextView? = null
    var desc: TextView? = null
    var mCurrentCartUri: Uri? = null
    var hasAllRequiredValues = false
    var quantity = 0


    override fun onCreate(savedInstanceState: Bundle?) {

        this.title = "Detail Menu"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        addtoCart = findViewById(R.id.addtocart)
        addtoCart?.visibility = View.VISIBLE // or View.INVISIBLE or View.GONE depending on your requirement
        drinkName = findViewById(R.id.detail_Name)
        coffeePrice = findViewById(R.id.item_price)
        desc = findViewById(R.id.detail_description)

        val id = intent.getIntExtra("id", -1)
        val list: ArrayList<Food> = FoodData.getData(applicationContext)
        // setting the name of drink


        // setting the name of drink
       // drinkName.setText("Green Tea")

        val food = list[id]
        val image = findViewById<ImageView>(R.id.detail_photo)
        image.setImageDrawable(food.image)
        drinkName?.text = food.Name
        coffeePrice?.text = food.Price
        desc?.text = food.Description

        addtoCart?.setOnClickListener(View.OnClickListener {
            LoaderManager.getInstance(this).initLoader(1, null, this)

            val intent = Intent(this@DetailActivity, SummaryActivity::class.java)
            startActivity(intent)
            // once this button is clicked we want to save our values in the database and send those values
            // right away to summary activity where we display the order info
            saveCart()
        })


    }

    private fun displayQuantity() {
        quantitynumber?.setText(quantity.toString())
    }

    private fun saveCart(): Boolean {

        // getting the values from our views
        val name: String = drinkName?.text.toString()
        val price: String = coffeePrice?.text.toString()
        val quantity: String = quantitynumber?.text.toString()
        val values = ContentValues()
        values.put(OrderContract.OrderEntry.COLUMN_NAME, name)
        values.put(OrderContract.OrderEntry.COLUMN_PRICE, price)
        values.put(OrderContract.OrderEntry.COLUMN_QUANTITY, quantity)


        if (mCurrentCartUri == null) {
            val newUri = contentResolver.insert(OrderContract.OrderEntry.CONTENT_URI, values)
            if (newUri == null) {
                Toast.makeText(this, "Failed to add to Cart", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Success  adding to Cart", Toast.LENGTH_SHORT).show()
            }
        }
        hasAllRequiredValues = true
        return hasAllRequiredValues
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        if (cursor == null || cursor.count < 1) {
            return
        }

        if (cursor.moveToFirst()) {
            val name = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME)
            val price = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE)
           // val quantity = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY)

            val nameofdrink = cursor.getString(name)
            val priceofdrink = cursor.getString(price)
            //val quantityofdrink = cursor.getString(quantity)

            drinkName?.text = nameofdrink ?: ""
            coffeePrice?.text = priceofdrink ?: ""
           // quantitynumber?.text = quantityofdrink ?: ""
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(
            OrderContract.OrderEntry._ID,
            OrderContract.OrderEntry.COLUMN_NAME,
            OrderContract.OrderEntry.COLUMN_PRICE,
            OrderContract.OrderEntry.COLUMN_QUANTITY )

        return CursorLoader(
            this,
            mCurrentCartUri ?: Uri.EMPTY,  // Replace with the URI of your content provider
            projection,
            null,
            null,
            null
        )
    }


    override fun onLoaderReset(loader: Loader<Cursor>) {
        drinkName?.text = ""
        coffeePrice?.text = ""
        quantitynumber?.text = ""
    }


}