package me.manulorenzo.moneytransactions.data.repository

import me.manulorenzo.moneytransactions.data.model.AccountData

interface Repository {
    suspend fun getAccount(): AccountData?
    suspend fun getTransactionString(fileName: String = "transactions.json"): String
}