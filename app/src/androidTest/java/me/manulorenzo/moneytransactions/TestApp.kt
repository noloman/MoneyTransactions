package me.manulorenzo.moneytransactions

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { }
        AndroidThreeTen.init(this)
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}