package shafiei.homa.paraf.feature.slider

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object DeviceConfiguration {

    lateinit var displayMetrics: DisplayMetrics
        private set

    val widthPixels: Int
        get() = displayMetrics.widthPixels

    val heightPixels: Int
        get() = displayMetrics.heightPixels


    fun init(context: Context) {
        displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
            .defaultDisplay
            .getMetrics(displayMetrics)
    }

}