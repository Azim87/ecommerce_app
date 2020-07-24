package kg.azimus.ecommerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kg.azimus.util.ActivityHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        welcome_btn.setOnClickListener {
            //LoginActivity.start(this)
            ActivityHelper().start<LoginActivity>(this)
        }
        join_btn.setOnClickListener {
            ActivityHelper().start<RegisterActivity>(this)
        }
    }
}