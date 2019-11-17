package me.manulorenzo.moneytransactions.shared.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountViewModelModule = module { viewModel { AccountViewModel(get(), get()) } }