package com.example.lab4workmanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
class Product() {
    @PrimaryKey()
    var id: Int? = null
    var  title: String? = null
    var description: String? = null
    var price: Double?= null
    var thumbnail : String? = null

    constructor(id: Int, title: String, description: String, category: String, price: Double, discountPercentage: Double, rating: Double, stock: Int , thumbnail : String):this(){
        this.title=title
        this.price=price
        this.thumbnail=thumbnail
    }

}