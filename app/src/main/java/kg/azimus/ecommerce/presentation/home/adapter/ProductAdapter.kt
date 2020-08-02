package kg.azimus.ecommerce.presentation.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        val progressBar: ProgressBar = itemView.findViewById(R.id.item_progress_bar)

        fun bind(products: Products) {
            name.text = products.name
            progressBar.visibility = View.VISIBLE
            Glide.with(imageView.context)
                .load(products.image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.INVISIBLE
                        return false
                    }
                }).into(imageView)
            price.text = products.price
            description.text = products.description
            category.text = products.category
        }
    }
}