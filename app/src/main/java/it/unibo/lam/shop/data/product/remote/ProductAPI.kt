package it.unibo.lam.shop.data.product.remote

import it.unibo.lam.shop.data.product.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductAPI {
    @GET("api/v1/products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("api/v1/products/{productID}")
    suspend fun getProduct(@Path("productID") productID: Int): Response<Product>
}