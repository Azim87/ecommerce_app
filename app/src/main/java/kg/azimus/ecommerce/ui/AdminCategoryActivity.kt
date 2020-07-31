package kg.azimus.ecommerce.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kg.azimus.ecommerce.R
import kotlinx.android.synthetic.main.activity_admin_category.*

class AdminCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_category)

        setCategoryIntent()
    }

    private fun setCategoryIntent() {
        t_shirts.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "TShirt")
            startActivity(intent)
        }
        sports_t_shirts.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Sport Shirts")
            startActivity(intent)
        }
        female_dresses.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Female Dresses")
            startActivity(intent)
        }
        sweaters.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Sweaters")
            startActivity(intent)
        }

        glasses.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Glasses")
            startActivity(intent)
        }
        purses_bags_wallets.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Purses and Wallets")
            startActivity(intent)
        }
        hats_caps.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Hats and Caps")
            startActivity(intent)
        }
        shoes.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Shoes")
            startActivity(intent)
        }

        headphones_handfree.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Headphones")
            startActivity(intent)
        }
        laptop_pc.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Laptops")
            startActivity(intent)
        }
        watches.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Watches")
            startActivity(intent)
        }
        mobilephones.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Mobile Phones")
            startActivity(intent)
        }
    }
}