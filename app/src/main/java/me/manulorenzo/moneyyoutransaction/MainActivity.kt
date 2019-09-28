package me.manulorenzo.moneyyoutransaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.manulorenzo.moneyyoutransaction.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                // TODO Why commitNow?
                .commitNow()
        }
    }
}
