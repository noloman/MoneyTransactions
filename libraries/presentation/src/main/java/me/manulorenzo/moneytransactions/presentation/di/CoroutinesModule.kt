package me.manulorenzo.moneytransactions.presentation.di

import me.manulorenzo.moneytransactions.presentation.CoroutinesContextProvider
import org.koin.dsl.module

val coroutinesModule = module {
    single { CoroutinesContextProvider() }
}