package me.manulorenzo.moneytransactions.shared.di

import me.manulorenzo.moneytransactions.shared.CoroutinesContextProvider
import org.koin.dsl.module

val coroutinesModule = module {
    single { CoroutinesContextProvider() }
}