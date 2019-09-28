package me.manulorenzo

import android.app.Application
import android.os.StrictMode
import me.manulorenzo.moneyyoutransaction.BuildConfig
import me.manulorenzo.moneyyoutransaction.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

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
        startKoin {
            androidLogger()
            androidContext(this@MoneyYouApplication)
            modules(dataModule)
        }
    }
}