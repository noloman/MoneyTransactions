package me.manulorenzo.moneyyoutransaction.data.model.ui

data class AccountEntity(
    val account: String,
    val balance: String,
    var transactions: List<TransactionEntity>?
)