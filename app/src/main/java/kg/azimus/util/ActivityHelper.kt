package kg.azimus.util

import android.app.Activity
import android.content.Context
import android.content.Intent

class ActivityHelper {
    inline fun <reified T: Activity> start(context: Context) {
        context.startActivity(Intent(context, T::class.java))
    }
}