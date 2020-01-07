package me.manulorenzo.moneytransactions.data_account

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object MoshiWrapper {
    val moshiAccountDataAdapter: JsonAdapter<AccountData> =
        Moshi.Builder().build().adapter<AccountData>(
            AccountData::class.java
        )
}