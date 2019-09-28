package me.manulorenzo.moneyyoutransaction.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    val account: String,
    val balance: String,
    val transactions: List<Transaction>
)