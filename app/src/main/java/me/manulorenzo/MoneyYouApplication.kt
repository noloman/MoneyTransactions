package me.manulorenzo

import android.app.Application
import android.os.StrictMode
import me.manulorenzo.moneyyoutransaction.BuildConfig

class MoneyYouApplication : Application() {
    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyDeath()
                    .build()
            )
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build())
        }
        super.onCreate()
    }
}