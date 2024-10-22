package com.example.cafemenuapp.Database

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.cafemenuapp.Database.OrderContract.Companion.PATH

class OrderProvider : ContentProvider() {


    var mHelper: OrderHelper? = null
    override fun onCreate(): Boolean {
        mHelper = OrderHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        // since we are querying from the databse
        val database = mHelper!!.readableDatabase
        val cursor: Cursor
        val match = sUriMatcher.match(uri)
        cursor = when (match) {
            ORDER -> database.query(
                OrderContract.OrderEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )

            else -> throw IllegalArgumentException("CANT QUERY")
        }
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        val match = sUriMatcher.match(uri)
        return when (match) {
            ORDER -> CONTENT_LIST_TYPE
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val match = sUriMatcher.match(uri)
        return when (match) {
            ORDER -> insertCart(uri, values)
            else -> throw IllegalArgumentException("Cant insert data")
        }
    }

    private fun insertCart(uri: Uri, values: ContentValues?): Uri? {
        val name = values!!.getAsString(OrderContract.OrderEntry.COLUMN_NAME)
            ?: throw IllegalArgumentException("Name is Required")
        val quantity = values.getAsString(OrderContract.OrderEntry.COLUMN_QUANTITY)
            ?: throw IllegalArgumentException("quantity is Required")
        val price = values.getAsString(OrderContract.OrderEntry.COLUMN_PRICE)
            ?: throw IllegalArgumentException("price is Required")

        // SINCE WE ARE INSERTING DATA IN DATABASE SO NOW WE ARE WRITING ON DATABASE
        val database = mHelper!!.writableDatabase
        val id = database.insert(OrderContract.OrderEntry.TABLE_NAME, null, values)
        if (id == -1L) {
            return null
        }
        context!!.contentResolver.notifyChange(uri, null)
        return ContentUris.withAppendedId(uri, id)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {

        // we will use this to clear the data once order is made
        val rowsDeleted: Int
        val database = mHelper!!.writableDatabase
        val match = sUriMatcher.match(uri)
        rowsDeleted = when (match) {
            ORDER -> database.delete(
                OrderContract.OrderEntry.TABLE_NAME,
                selection,
                selectionArgs
            )

            else -> throw IllegalArgumentException("Cannot delete")
        }
        if (rowsDeleted != 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return rowsDeleted
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val match = sUriMatcher.match(uri)
        return when (match) {
            ORDER -> {
                // Implement your update logic here if needed
                0 // Placeholder, update not supported yet
            }
            else -> throw IllegalArgumentException("Invalid URI for update: $uri")
        }
    }


    companion object {
        // this constant is needed in order to define the path of our modification in the table

        const val CONTENT_AUTHORITY = "com.example.cafemenuapp"
        var CONTENT_LIST_TYPE = "vnd.android.cursor.dir/$CONTENT_AUTHORITY/$PATH"

      //  const val PATH = "orders"


        const val ORDER = 100
        var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(OrderContract.CONTENT_AUTHORITY, PATH, ORDER)
        }
    }
}