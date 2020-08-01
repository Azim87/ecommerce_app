package kg.azimus.ecommerce.presentation

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kg.azimus.ecommerce.R
import kg.azimus.ecommerce.util.ActivityHelper
import kg.azimus.ecommerce.util.toast
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
            val phoneNumber = register_phone_number.text.toString().trim()
            val password = register_password.text.toString().trim()
            createAccount(userName, phoneNumber, password)
        }
    }

    private fun createAccount(
        userName: String,
        phoneNumber: String,
        password: String
    ) {
        when {
            TextUtils.isEmpty(userName) -> {
                toast(this, "name is empty")
            }
            TextUtils.isEmpty(phoneNumber) -> {
                toast(this, "email is empty")
            }
            TextUtils.isEmpty(password) -> {
                toast(this, "password is empty")
            }
            else -> {
                showLoading(true)
                validateUserInfo(userName, phoneNumber, password)
            }
        }
        Log.d(TAG, "createAccount: create an account $userName  $phoneNumber  $password ")
    }

    private fun validateUserInfo(
        userName: String,
        phoneNumber: String,
        password: String
    ) {
        val dataBase = FirebaseDatabase.getInstance()
        val myRef = dataBase.reference

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!(snapshot.child("Users").child(phoneNumber).exists())) {
                    val userDataMap: HashMap<String, Any> = HashMap()
                    userDataMap["userName"] = userName
                    userDataMap["phoneNumber"] = phoneNumber
                    userDataMap["password"] = password
                    myRef.child("Users").child(phoneNumber)
                        .updateChildren(userDataMap as Map<String, Any>)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                toast(
                                    this@RegisterActivity,
                                    "Congratulation! Account has been created."
                                )
                                showLoading(false)
                                ActivityHelper.start<LoginActivity>(this@RegisterActivity)
                            } else {
                                toast(this@RegisterActivity, "Network error " + task.exception)
                                showLoading(false)
                            }
                        }

                } else {
                    toast(this@RegisterActivity, "This $phoneNumber already exist")
                    showLoading(false)
                    ActivityHelper.start<MainActivity>(this@RegisterActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                toast(this@RegisterActivity, "Error ${error.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        register_progress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}