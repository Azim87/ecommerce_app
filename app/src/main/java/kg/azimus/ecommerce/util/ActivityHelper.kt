package kg.azimus.ecommerce.util

import android.app.Activity
import android.content.Context
import android.content.Intent

class ActivityHelper {

    companion object {
        inline fun <reified T : Activity> start(context: Context) {
            context.startActivity(Intent(context, T::class.java))
        }
    }
}