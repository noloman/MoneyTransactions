package me.manulorenzo.moneytransactions.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.manulorenzo.moneytransactions.data.model.AccountData

object MoshiWrapper {
    val moshiAccountDataAdapter: JsonAdapter<AccountData> =
        Moshi.Builder().build().adapter<AccountData>(AccountData::class.java)
}