package me.manulorenzo.moneytransactions.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionData(
    val id: String,
    val amount: String,
    val description: String,
    val otherAccount: String,
    val date: String
)