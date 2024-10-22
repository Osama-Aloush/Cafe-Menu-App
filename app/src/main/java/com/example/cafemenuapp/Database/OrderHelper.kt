package com.example.cafemenuapp.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OrderHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABSE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val SQL_TABLE = ("CREATE TABLE " + OrderContract.OrderEntry.TABLE_NAME + " ("
                + OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrderContract.OrderEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_QUANTITY + " TEXT NOT NULL, "
                + OrderContract.OrderEntry.COLUMN_PRICE + " TEXT NOT NULL"
                + ")")
        db.execSQL(SQL_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABSE_NAME = "ord.db"
    }
}