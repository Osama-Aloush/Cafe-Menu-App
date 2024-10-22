package com.example.cafemenuapp.Database

import android.net.Uri
import android.provider.BaseColumns


class OrderContract {

    // contentauthority requires you to enter your package name


    // this should be similar to your table name

    object OrderEntry : BaseColumns {

        val CONTENT_URI: Uri = Uri.withAppendedPath(BASE_URI, PATH)

        const val TABLE_NAME = "ordering"
        const val _ID = BaseColumns._ID
        const val COLUMN_NAME = "name"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_PRICE = "price"
    }

    companion object {
        val CONTENT_AUTHORITY = "com.example.cafemenuapp"
        val BASE_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")
        val PATH = "ordering"


    }
}
