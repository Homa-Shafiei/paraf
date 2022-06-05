package shafiei.homa.paraf

import android.annotation.SuppressLint
import android.app.Application
import shafiei.homa.paraf.feature.slider.DeviceConfiguration

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        DeviceConfiguration.init(applicationContext)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: MyApplication
    }

}