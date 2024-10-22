package com.example.cafemenuapp

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.cafemenuapp.Database.OrderContract

class CartAdapter(context: Context?, cursor: Cursor?) : CursorAdapter(context, cursor, 0) {
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.cartlist, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {

        // getting theviews
        val drinkName: TextView
        val price: TextView
        drinkName = view.findViewById(R.id.drinkNameinOrderSummary)
        price = view.findViewById(R.id.priceinOrderSummary)

        // getting the values by first getting the position of their columns
        val name = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME)
        val priceofdrink = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE)
        val nameofdrink = cursor.getString(name)
        val pricesofdrink = cursor.getString(priceofdrink)
        drinkName.text = nameofdrink
        price.text = pricesofdrink
    }
}