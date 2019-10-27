package me.manulorenzo.moneytransactions.data_transaction

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionData(
    val id: String,
    val amount: String,
    val description: String,
    val otherAccount: String,
    val date: String
)