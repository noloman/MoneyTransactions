package me.manulorenzo.moneytransactions.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.manulorenzo.moneytransactions.data.model.AccountData
import me.manulorenzo.moneytransactions.data.model.ui.Account

open class MoshiWrapper {
    val moshiAccountDataAdapter: JsonAdapter<AccountData> =
        Moshi.Builder().build().adapter<AccountData>(AccountData::class.java)
    val moshiAccountAdapter: JsonAdapter<Account> =
        Moshi.Builder().build().adapter<Account>(Account::class.java)
}