package kg.azimus.ecommerce.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kg.azimus.ecommerce.R
import kg.azimus.ecommerce.util.ActivityHelper
import kotlinx.android.synthetic.main.activity_home.*

private const val TAG = "HomeActivity"
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG, "onCreate: start Home activity")

        logout_home.setOnClickListener {
            ActivityHelper.start<LoginActivity>(this)
            finish()
        }
    }
}