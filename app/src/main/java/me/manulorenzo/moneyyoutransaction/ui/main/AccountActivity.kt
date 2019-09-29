package me.manulorenzo.moneyyoutransaction.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.manulorenzo.moneyyoutransaction.R

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AccountFragment.newInstance())
                // TODO Why commitNow?
                .commitNow()
        }
    }
}
