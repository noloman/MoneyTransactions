package me.manulorenzo.moneytransactions.core

import me.manulorenzo.moneytransactions.data_account.AccountData

interface Repository {
    suspend fun getAccount(): AccountData?
    suspend fun getTransactionString(fileName: String = "transactions.json"): String
}