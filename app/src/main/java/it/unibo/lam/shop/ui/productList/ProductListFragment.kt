package it.unibo.lam.shop.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import it.unibo.lam.shop.R
import it.unibo.lam.shop.data.product.remote.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A fragment representing a list of Items.
 */
class ProductListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false) as RecyclerView

        val adapter = MyProductLListRecyclerViewAdapter()

        GlobalScope.launch(Dispatchers.IO) {
            val products = ProductRepository.getProducts()
            withContext(Dispatchers.Main) {
                // Update the LiveData with the list of products
                if (products != null) {
                    adapter.setProducts(products)
                }
            }
        }
        view.adapter = adapter

        return view
    }

    companion object {
        fun newInstance(): ProductListFragment {
            return ProductListFragment()
        }
    }

}