package kg.azimus.ecommerce.util

import android.content.Context
import android.widget.Toast

fun Context.toast(context: Context = applicationContext, message: String){
    Toast.makeText(context, message , Toast.LENGTH_SHORT).show()
}