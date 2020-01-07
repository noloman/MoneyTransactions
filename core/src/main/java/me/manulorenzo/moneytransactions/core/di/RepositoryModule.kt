package me.manulorenzo.moneytransactions.core.di

import me.manulorenzo.moneytransactions.core.Repository
import me.manulorenzo.moneytransactions.core.RepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single {
        RepositoryImpl(
            get(),
            get()
        ) as Repository
    }
}