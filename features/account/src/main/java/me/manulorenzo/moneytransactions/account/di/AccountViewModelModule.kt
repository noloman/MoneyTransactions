package me.manulorenzo.moneytransactions.account.di

import me.manulorenzo.moneytransactions.account.AccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountViewModelModule = module {
    viewModel {
        AccountViewModel(
            get(),
            get()
        )
    }
}