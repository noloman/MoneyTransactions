package me.manulorenzo.moneyyoutransaction.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.manulorenzo.moneyyoutransaction.data.model.Account

object MoshiModule {
    val moshiAccountAdapter: JsonAdapter<Account> =
        Moshi.Builder().build().adapter<Account>(Account::class.java)
}