package me.manulorenzo.moneytransactions.data_account.di

import me.manulorenzo.moneytransactions.data_account.MoshiWrapper
import org.koin.dsl.module

val moshiModule = module { single { MoshiWrapper.moshiAccountDataAdapter } }