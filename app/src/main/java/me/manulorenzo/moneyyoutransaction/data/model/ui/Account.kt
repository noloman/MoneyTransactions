package me.manulorenzo.moneyyoutransaction.data.model.ui

data class Account(
    val account: String,
    val balance: String,
    var transactions: List<Transaction>?
)