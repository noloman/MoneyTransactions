package me.manulorenzo.moneytransactions

import android.app.Application
import android.os.StrictMode
import me.manulorenzo.moneytransactions.account.di.accountViewModelModule
import me.manulorenzo.moneytransactions.core.di.repositoryModule
import me.manulorenzo.moneytransactions.shared.di.coroutinesModule
import me.manulorenzo.moneytransactions.shared.di.moshiAccountDataAdapterModule
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
        startKoin {
            androidLogger()
            androidContext(this@MoneyTransactionsApplication)
            modules(
                listOf(
                    repositoryModule,
                    coroutinesModule,
                    accountViewModelModule,
                    moshiAccountDataAdapterModule
                )
            )
        }
    }
}