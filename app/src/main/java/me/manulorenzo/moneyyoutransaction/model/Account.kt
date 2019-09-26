package me.manulorenzo.moneyyoutransaction.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    val account: String,
    val balance: String,
    val transactions: List<Transaction>
)