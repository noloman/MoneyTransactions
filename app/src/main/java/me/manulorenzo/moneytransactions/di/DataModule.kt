package me.manulorenzo.moneytransactions.di

import me.manulorenzo.moneytransactions.data.repository.Repository
import me.manulorenzo.moneytransactions.data.repository.RepositoryImpl
import me.manulorenzo.moneytransactions.ui.main.AccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { MoshiWrapper() }
    single {
        RepositoryImpl(
            get(),
            get()
        ) as Repository
    }
    viewModel { AccountViewModel(get(), get()) }
}