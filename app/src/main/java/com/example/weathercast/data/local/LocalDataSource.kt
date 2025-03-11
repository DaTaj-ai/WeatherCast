package com.example.mvvm_lab3.data.localDataSource

import com.example.lab4workmanager.database.ProductDao
import com.example.lab4workmanager.models.Product

class LocalDataSource(private val dao: ProductDao) {

    suspend fun deleteProduct(product: Product): Int {
        return dao.deleteProduct(product)
    }

    suspend fun insertProduct(movie: Product) : Long {
        return dao.insertProduct(movie)
    }

    suspend fun getAllProducts(): List<Product> {
        return dao.getAllProducts()
    }


}