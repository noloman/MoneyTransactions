package me.manulorenzo.moneytransactions.data_account

import com.squareup.moshi.JsonClass
import me.manulorenzo.moneytransactions.data_transaction.Transaction

@JsonClass(generateAdapter = true)
data class Account(
    val account: String,
    val balance: String,
    var transactions: List<Transaction>?
)