package kg.azimus.ecommerce

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kg.azimus.util.toast
import kotlinx.android.synthetic.main.activity_register.*

private const val TAG = "RegisterActivity"

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        onCreateAccountButtonClick()
    }

    private fun onCreateAccountButtonClick() {
        register_btn.setOnClickListener {
            Log.d(TAG, "onCreateAccountClick: create account button click")
            val userName = register_username.text.toString().trim()
            val email = register_email.text.toString().trim()
            val password = register_password.text.toString().trim()
            createAccount(userName, email, password)
        }
    }

    private fun createAccount(
        userName: String,
        email: String,
        password: String
    ) {
        when {
            TextUtils.isEmpty(userName) -> {
                toast(this, "name is empty")
            }
            TextUtils.isEmpty(email) -> {
                toast(this, "email is empty")
            }
            TextUtils.isEmpty(password) -> {
                toast(this, "password is empty")
            }
            else -> {
                showLoading(true)
            }
        }
        Log.d(TAG, "createAccount: create an account $userName  $email  $password ")
    }

    private fun showLoading(isLoading: Boolean) {
        register_progress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}