package me.manulorenzo.moneytransactions.transactions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.transactionFragment,
                    TransactionFragment.newInstance(intent.getParcelableExtra("extra")!!)
                ).commit()
        }
    }
}
