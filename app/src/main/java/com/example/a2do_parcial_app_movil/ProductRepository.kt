package com.example.a2do_parcial_app_movil

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductRepository(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "product_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PRODUCTS = "products"
        private const val KEY_CODE = "code"
        private const val KEY_PRODUCT_NAME = "product_name"
        private const val KEY_PRICE = "price"
        private const val KEY_DISCOUNT = "discount"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_PRODUCTS ($KEY_CODE TEXT PRIMARY KEY, $KEY_PRODUCT_NAME TEXT, $KEY_PRICE TEXT, $KEY_DISCOUNT TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    fun addProduct(product: Product) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_CODE, product.code)
            put(KEY_PRODUCT_NAME, product.productName)
            put(KEY_PRICE, product.price)
            put(KEY_DISCOUNT, product.discount)
        }

        db.insert(TABLE_PRODUCTS, null, values)
        db.close()
    }

    fun getProduct(code: String): Product? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_PRODUCTS,
            arrayOf(KEY_CODE, KEY_PRODUCT_NAME, KEY_PRICE, KEY_DISCOUNT),
            "$KEY_CODE=?",
            arrayOf(code),
            null,
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            Product(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
        } else null
    }

    fun updateProduct(product: Product) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_PRODUCT_NAME, product.productName)
            put(KEY_PRICE, product.price)
            put(KEY_DISCOUNT, product.discount)
        }

        db.update(
            TABLE_PRODUCTS,
            values,
            "$KEY_CODE = ?",
            arrayOf(product.code)
        )
        db.close()
    }

    fun deleteProduct(code: String) {
        val db = this.writableDatabase
        db.delete(TABLE_PRODUCTS, "$KEY_CODE = ?", arrayOf(code))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val selectQuery = "SELECT * FROM $TABLE_PRODUCTS"

        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val code = cursor.getString(cursor.getColumnIndex(KEY_CODE))
                val productName = cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME))
                val price = cursor.getString(cursor.getColumnIndex(KEY_PRICE))
                val discount = cursor.getString(cursor.getColumnIndex(KEY_DISCOUNT))

                val movie = Product(code, productName, price, discount)
                productList.add(movie)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return productList
    }

}