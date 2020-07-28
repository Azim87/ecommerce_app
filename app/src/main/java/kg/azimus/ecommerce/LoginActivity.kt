package kg.azimus.ecommerce

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.paperdb.Paper
import kg.azimus.model.UserModel
import kg.azimus.util.ActivityHelper
import kg.azimus.util.Prevalent
import kg.azimus.util.toast
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "LoginActivity"
private var DB_NAME = "Users"

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onLogInButtonClick()
        onAdminClick()
        onNotAdminClick()
        Paper.init(this)
    }

    private fun onLogInButtonClick() {
        Log.d(TAG, "onLogIn: login")
        login_btn.setOnClickListener {
            val phoneNumber = login_phone_number.text.toString().trim()
            val password = login_password.text.toString().trim()
            loginUser(phoneNumber, password)
        }
    }

    private fun loginUser(phoneNumber: String, password: String) {
        when {
            TextUtils.isEmpty(phoneNumber) -> {
                toast(this, "email is empty")
            }
            TextUtils.isEmpty(password) -> {
                toast(this, "password is empty")
            }
            else -> {
                showLoading(true)
                allowAccessToAccount(phoneNumber, password)
            }
        }
        Log.d(TAG, "loginUser: login as $phoneNumber  $password ")
    }

    private fun allowAccessToAccount(phoneNumber: String, password: String) {
        if (remember_me.isChecked) {
            Paper.book().write(Prevalent.UserPhoneKey, phoneNumber)
            Paper.book().write(Prevalent.UserPasswordKey, password)
        }

        val dataBase = Firebase.database
        val myRef = dataBase.getReference("User")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(DB_NAME).child(phoneNumber).exists()) {
                    val userModel: UserModel? =
                        snapshot.child(DB_NAME).child(phoneNumber).getValue(UserModel::class.java)
                    if (userModel!!.phoneNumber.equals(phoneNumber)) {
                        if (userModel.password.equals(password)) {
                            if (DB_NAME == "Admin") {
                                toast(this@LoginActivity, "Login Successful.")
                                ActivityHelper.start<AdminActivity>(this@LoginActivity)
                                finish()
                                showLoading(false)
                                clearUserData()
                            } else if (DB_NAME == "Users") {
                                toast(this@LoginActivity, "Login Successful.")
                                ActivityHelper.start<HomeActivity>(this@LoginActivity)
                                finish()
                                showLoading(false)
                                clearUserData()
                            }

                        } else {
                            toast(this@LoginActivity, "Password is incorrect")
                            showLoading(false)
                        }
                    }
                } else {
                    toast(this@LoginActivity, "Account with this $phoneNumber does not exist")
                    showLoading(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                toast(this@LoginActivity, "Error ${error.message}")
            }
        })
    }

    private fun onAdminClick() {
        login_an_admin.setOnClickListener {
            Log.d(TAG, "onAdminClick: admin click")
            login_btn.text = "Login as Admin"
            login_an_admin.visibility = View.INVISIBLE
            login_non_admin.visibility = View.VISIBLE
            DB_NAME = "Admin"
        }
    }

    private fun onNotAdminClick() {
        login_non_admin.setOnClickListener {
            Log.d(TAG, "onNotAdminClick: not admin click")
            login_btn.text = "Login"
            login_an_admin.visibility = View.VISIBLE
            login_non_admin.visibility = View.INVISIBLE
            DB_NAME = "Users"
        }
    }

    private fun clearUserData() {
        login_phone_number.text.clear()
        login_password.text.clear()
    }

    private fun showLoading(isLoading: Boolean) {
        login_progress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE

    }
}