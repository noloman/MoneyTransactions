package me.manulorenzo.moneyyoutransaction.data

import me.manulorenzo.moneyyoutransaction.model.data.Account

interface Repository {
    fun getAccount(): Account?
}