package kg.azimus.ecommerce.presentation.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kg.azimus.ecommerce.R
import kg.azimus.ecommerce.model.Products

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val mProductsList: ArrayList<Products> = ArrayList()

    fun setList(productsList: ArrayList<Products>) {
        mProductsList.clear()
        mProductsList.addAll(productsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.bind(mProductsList[position])
    }

    override fun getItemCount(): Int = mProductsList.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item_product_name)
        val imageView: ImageView = itemView.findViewById(R.id.item_product_image)
        val price: TextView = itemView.findViewById(R.id.item_product_price)
        val description: TextView = itemView.findViewById(R.id.item_product_description)
        val category: TextView = itemView.findViewById(R.id.item_product_category)

        fun bind(products: Products) {
            name.text = products.name
            Picasso.get().load(Uri.parse(products.image)).into(imageView)
            price.text = products.price
            description.text = products.description
            category.text = products.category
        }
    }
}