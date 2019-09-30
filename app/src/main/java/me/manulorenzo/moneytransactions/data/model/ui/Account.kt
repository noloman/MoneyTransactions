package me.manulorenzo.moneytransactions.data.model.ui

data class Account(
    val account: String,
    val balance: String,
    var transactions: List<Transaction>?
)