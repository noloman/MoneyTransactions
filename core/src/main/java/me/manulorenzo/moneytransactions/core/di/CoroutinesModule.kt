package me.manulorenzo.moneytransactions.core.di

import me.manulorenzo.moneytransactions.core.CoroutinesContextProvider
import org.koin.dsl.module

val coroutinesModule = module {
    single { CoroutinesContextProvider() }
}