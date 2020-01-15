package me.manulorenzo.moneytransactions.core.di

import me.manulorenzo.moneytransactions.core.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        Repository(
            assetManager = get(),
            moshiJsonAccountDataAdapter = get()
        )
    }
}