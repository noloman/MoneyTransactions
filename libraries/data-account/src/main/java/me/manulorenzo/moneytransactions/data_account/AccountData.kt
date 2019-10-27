package me.manulorenzo.moneytransactions.data_account

import com.squareup.moshi.JsonClass
import me.manulorenzo.moneytransactions.data_transaction.TransactionData

@JsonClass(generateAdapter = true)
data class AccountData(
    val account: String,
    val balance: String,
    val transactions: List<TransactionData>
)