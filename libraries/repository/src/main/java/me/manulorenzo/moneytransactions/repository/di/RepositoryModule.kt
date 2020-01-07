package me.manulorenzo.moneytransactions.repository.di

import me.manulorenzo.moneytransactions.repository.Repository
import me.manulorenzo.moneytransactions.repository.RepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single {
        RepositoryImpl(
            get(),
            get()
        ) as Repository
    }
}