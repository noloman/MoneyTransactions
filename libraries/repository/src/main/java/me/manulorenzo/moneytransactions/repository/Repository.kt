package me.manulorenzo.moneytransactions.repository

interface Repository {
    suspend fun getAccount(): me.manulorenzo.moneytransactions.data_account.AccountData?
    suspend fun getTransactionString(fileName: String = "transactions.json"): String
}