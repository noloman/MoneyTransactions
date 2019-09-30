package me.manulorenzo.moneytransactions.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountData(
    val account: String,
    val balance: String,
    val transactions: List<TransactionData>
)