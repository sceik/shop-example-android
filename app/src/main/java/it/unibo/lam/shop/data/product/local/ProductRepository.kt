package it.unibo.lam.shop.data.product.local

import it.unibo.lam.shop.data.product.Category
import it.unibo.lam.shop.data.product.Product
import it.unibo.lam.shop.data.product.ProductRepository

object ProductRepository : ProductRepository() {

    private var products: MutableList<Product>

    init {
        val ELEMENT = 25
        products = ArrayList()
        for (i in 1..ELEMENT) {
            products.add(createProductItem(i))
        }
    }

    override suspend fun getProducts(): List<Product>? {
        return products
    }

    override suspend fun getProduct(productID: Int): Product? {
        return products.find {
            it.id == productID
        }
    }

    private fun createProductItem(position: Int): Product {
        return Product(
            position,
            "My Awsome product with id " + position,
            makeDescription(position),
            300,
            Category(10, "Elettronica", ""),
            listOf(makeImage(position))
        )
    }

    private fun makeDescription(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..(position % 5)) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    private fun makeImage(position: Int): String {
        return "product${position % 5}"
    }

}
