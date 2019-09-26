package me.manulorenzo.moneyyoutransaction.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaction(
    val id: String,
    val amount: String,
    val description: String,
    val otherAccount: String,
    val date: String
)