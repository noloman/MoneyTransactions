package me.manulorenzo.moneyyoutransaction.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.manulorenzo.moneyyoutransaction.data.model.AccountData

object MoshiModule {
    val moshiAccountDataAdapter: JsonAdapter<AccountData> =
        Moshi.Builder().build().adapter<AccountData>(AccountData::class.java)
}