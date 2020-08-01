package kg.azimus.ecommerce.presentation

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
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        sports_t_shirts.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Sport Shirts")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        female_dresses.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Female Dresses")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        sweaters.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Sweaters")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        glasses.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Glasses")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        purses_bags_wallets.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Purses and Wallets")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        hats_caps.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Hats and Caps")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        shoes.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Shoes")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        headphones_handfree.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Headphones")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        laptop_pc.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Laptops")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        watches.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Watches")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        mobilephones.setOnClickListener {
            val intent = Intent(AdminCategoryActivity@ this, AdminActivity::class.java)
            intent.putExtra("category", "Mobile Phones")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}