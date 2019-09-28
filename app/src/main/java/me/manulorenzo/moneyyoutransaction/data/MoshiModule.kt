package me.manulorenzo.moneyyoutransaction.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.manulorenzo.moneyyoutransaction.model.data.Account

object MoshiModule {
    val moshiAccountAdapter: JsonAdapter<Account> =
        Moshi.Builder().build().adapter<Account>(Account::class.java)
}