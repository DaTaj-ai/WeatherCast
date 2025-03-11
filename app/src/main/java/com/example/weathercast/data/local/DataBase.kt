package com.example.lab4workmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab4workmanager.models.Product

@Database(entities = arrayOf(Product::class), version = 1 )
abstract class ProductDataBase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao

    companion object{
        @Volatile
        private var INSTANCE: ProductDataBase? = null
        fun getInstance (ctx: Context): ProductDataBase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, ProductDataBase::class.java, "color_database")
                    .build()
                INSTANCE = instance
// return instance
                instance }
        }
    }
}
