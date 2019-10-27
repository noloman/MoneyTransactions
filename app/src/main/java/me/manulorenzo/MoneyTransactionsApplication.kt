package me.manulorenzo

import android.app.Application
import android.os.StrictMode
import di.accountViewModelModule
import me.manulorenzo.moneytransactions.BuildConfig
import me.manulorenzo.moneytransactions.data_account.di.moshiModule
import me.manulorenzo.moneytransactions.presentation.di.coroutinesModule
import me.manulorenzo.moneytransactions.repository.di.repositoryModule
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
                    moshiModule
                )
            )
        }
    }
}