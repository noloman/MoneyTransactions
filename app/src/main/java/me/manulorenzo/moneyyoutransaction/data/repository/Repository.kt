package me.manulorenzo.moneyyoutransaction.data.repository

import me.manulorenzo.moneyyoutransaction.data.model.Account

interface Repository {
    suspend fun getAccount(): Account?
    suspend fun getTransactionString(): String
}