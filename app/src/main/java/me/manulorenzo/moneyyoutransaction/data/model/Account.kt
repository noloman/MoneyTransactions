package me.manulorenzo.moneyyoutransaction.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    val account: String,
    val balance: String,
    val transactions: List<Transaction>
)