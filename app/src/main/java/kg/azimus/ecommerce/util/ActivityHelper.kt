package kg.azimus.ecommerce.util

import android.app.Activity
import android.content.Context
import android.content.Intent

class ActivityHelper {

    companion object {
        inline fun <reified T : Activity> start(context: Context) {
            val intent = Intent(context, T::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
        }
    }
}