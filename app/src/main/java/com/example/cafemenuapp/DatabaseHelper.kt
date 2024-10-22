package com.example.cafemenuapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val CREATE_CAFES_TABLE = (""
                + "CREATE TABLE " + TABLE_MENU + ""
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITTLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_PRICE + " TEXT"
                + ")")
        sqLiteDatabase.execSQL(CREATE_CAFES_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU)
        onCreate(sqLiteDatabase)
    }

    fun addData(menu: Food) {
        val sqLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put(KEY_TITTLE, menu.Name)
        values.put(KEY_DESCRIPTION, menu.Description)
        values.put(KEY_PRICE, menu.Price)
        sqLiteDatabase.insert(TABLE_MENU, null, values)
        sqLiteDatabase.close()
    }

    fun getFoodList(context: Context): List<Food> {
        val contactList: MutableList<Food> = ArrayList()
        // SELECT * FROM cafes
        val selectQuery = "SELECT * FROM " + TABLE_MENU + " LIMIT 6"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        val image: java.util.ArrayList<Int>? = FoodData.getImage()
        var index = 0
        if (cursor.moveToFirst()) {
            do {
                if (index % 6 == 0) {
                    index = 0
                }
                val Name = cursor.getString(1)
                val Description= cursor.getString(2)
                val Price = cursor.getString(3)
                /** val menu = Food(
                    Name, Description, Price,
                    context.getDrawable(image!![index])!!
                ) **/

                val menu = Food(Name, Description, Price, context.getDrawable(image!![index]))

                contactList.add(menu)
                index++
            } while (cursor.moveToNext())
        }
        return contactList
    }

    companion object {
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "CafeAppDB.sqlite"

        // Table Name
        private const val TABLE_MENU = "cafes"

        // Column Tables
        private const val KEY_ID = "id"
        private const val KEY_TITTLE = "Name"
        private const val KEY_DESCRIPTION = "Description"
        private const val KEY_PRICE = "Price"
    }
}