package me.manulorenzo.moneytransactions.data_account

import me.manulorenzo.moneytransactions.data_transaction.Transaction

data class Account(
    val account: String,
    val balance: String,
    var transactions: List<Transaction>?
)