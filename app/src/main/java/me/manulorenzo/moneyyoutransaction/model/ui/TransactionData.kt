package me.manulorenzo.moneyyoutransaction.model.ui

import java.util.Date

data class TransactionData(
    val amount: String,
    val description: String,
    val otherAccount: String,
    val date: Date
)