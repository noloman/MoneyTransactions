package me.manulorenzo.moneyyoutransaction.data.repository

import me.manulorenzo.moneyyoutransaction.data.model.AccountData

interface Repository {
    suspend fun getAccount(): AccountData?
    suspend fun getTransactionString(): String
}