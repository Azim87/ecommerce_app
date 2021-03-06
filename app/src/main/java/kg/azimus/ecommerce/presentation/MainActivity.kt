package kg.azimus.ecommerce.presentation

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.paperdb.Paper
import kg.azimus.ecommerce.R
import kg.azimus.ecommerce.model.UserModel
import kg.azimus.ecommerce.presentation.home.HomeActivity
import kg.azimus.ecommerce.util.ActivityHelper
import kg.azimus.ecommerce.util.Prevalent
import kg.azimus.ecommerce.util.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Paper.init(this)
        init()
    }

    private fun init() {
        welcome_btn.setOnClickListener {
            ActivityHelper.start<LoginActivity>(this)
        }
        join_btn.setOnClickListener {
            ActivityHelper.start<RegisterActivity>(this)
        }

        val userPhoneKey = Paper.book().read<String>(Prevalent.UserPhoneKey)
        val userPasswordKey = Paper.book().read<String>(Prevalent.UserPasswordKey)
        if (userPhoneKey != null && userPasswordKey != null) {

            if (!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey)) {
                allowAccess(userPhoneKey, userPasswordKey)
                showLoading(true)
            }
        }
    }

    private fun allowAccess(phoneNumber: String, password: String) {
        val dataBase = FirebaseDatabase.getInstance()
        val myRef = dataBase.reference

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("Users").child(phoneNumber).exists()) {
                    val userModel: UserModel? = snapshot
                        .child("Users")
                        .child(phoneNumber)
                        .getValue(UserModel::class.java)
                    if (userModel!!.phoneNumber.equals(phoneNumber)) {
                        if (userModel.password.equals(password)) {
                            toast(this@MainActivity, "Login Successful.")
                            ActivityHelper.start<HomeActivity>(this@MainActivity)
                            Prevalent.currentUserOnline = userModel
                            finish()
                            showLoading(false)
                        } else {
                            toast(this@MainActivity, "Password is incorrect")
                            showLoading(false)
                        }
                    }
                } else {
                    toast(this@MainActivity, "Account with this $phoneNumber does not exist")
                    showLoading(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                toast(this@MainActivity, "Error ${error.message}")
                showLoading(false)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        main_progress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}