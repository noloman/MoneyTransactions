package me.manulorenzo.moneytransactions.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.manulorenzo.moneytransactions.data_account.AccountData
import org.koin.dsl.module
import java.math.BigDecimal

private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
val moshiAccountDataAdapterModule =
    module { single { moshi.adapter<AccountData>(AccountData::class.java) } }
val moshiBigDecimalAdapterModule =
    module { single { moshi.adapter<BigDecimal>(BigDecimal::class.java) } }