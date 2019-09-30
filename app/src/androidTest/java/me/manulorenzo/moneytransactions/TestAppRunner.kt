package me.manulorenzo.moneytransactions

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestAppRunner : AndroidJUnitRunner() {
    override fun newApplication(
        loader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(loader, TestApp::class.java.name, context)
}