package me.manulorenzo.moneytransactions.repository.di

import org.koin.dsl.module

val repositoryModule = module {
    //        single { MoshiWrapper.moshiAccountDataAdapter }
    single {
        me.manulorenzo.moneytransactions.repository.RepositoryImpl(
            get(),
            get()
        ) as me.manulorenzo.moneytransactions.repository.Repository
    }
//        viewModel { AccountViewModel(get(), get()) }
}