package kg.azimus.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.azimus.util.ActivityHelper
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        logout_home.setOnClickListener {
            ActivityHelper.start<LoginActivity>(this)
            finish()
        }
    }
}