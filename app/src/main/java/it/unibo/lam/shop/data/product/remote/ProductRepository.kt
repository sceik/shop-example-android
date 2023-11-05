package it.unibo.lam.shop.data.product.remote

import android.util.Log
import it.unibo.lam.shop.data.product.Product
import it.unibo.lam.shop.data.product.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ProductRepository : ProductRepository(){

    var productAPI : ProductAPI;

    init {
        productAPI = getInstance().create(ProductAPI::class.java)
    }

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.escuelajs.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override suspend fun getProducts(): List<Product>? {
        return try {
            val response = productAPI.getProducts()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getProduct(productID: Int): Product? {
        return try {
            val response = productAPI.getProduct(productID)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
