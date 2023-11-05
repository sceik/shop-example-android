package it.unibo.lam.shop.data.product

abstract class ProductRepository {
    abstract suspend fun getProducts(): List<Product>?;
    abstract suspend fun getProduct(productID: Int): Product?;
}