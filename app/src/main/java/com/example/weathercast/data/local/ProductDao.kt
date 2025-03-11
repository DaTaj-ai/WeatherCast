package com.example.lab4workmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.lab4workmanager.models.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product):Long

    @Delete()
    suspend fun deleteProduct(product: Product):Int

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

}