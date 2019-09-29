package me.manulorenzo.moneyyoutransaction.di

import me.manulorenzo.moneyyoutransaction.util.CoroutineContextDelegate
import me.manulorenzo.moneyyoutransaction.util.CoroutineContextProvider
import org.koin.dsl.module

val coroutinesModule = module {
    single { CoroutineContextProvider() }
    single { CoroutineContextDelegate() }
}