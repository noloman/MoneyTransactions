package me.manulorenzo

import android.app.Application
import android.os.StrictMode
import com.jakewharton.threetenabp.AndroidThreeTen
import me.manulorenzo.moneytransactions.BuildConfig
import me.manulorenzo.moneytransactions.di.coroutinesModule
import me.manulorenzo.moneytransactions.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoneyTransactionsApplication : Application() {
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
        AndroidThreeTen.init(this)
        startKoin {
            androidLogger()
            androidContext(this@MoneyTransactionsApplication)
            modules(listOf(dataModule, coroutinesModule))
        }
    }
}