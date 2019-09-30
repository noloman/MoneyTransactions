package me.manulorenzo.moneytransactions.di

import me.manulorenzo.moneytransactions.util.CoroutineContextDelegate
import me.manulorenzo.moneytransactions.util.CoroutineContextProvider
import org.koin.dsl.module

val coroutinesModule = module {
    single { CoroutineContextProvider() }
    single { CoroutineContextDelegate() }
}