package me.manulorenzo.moneyyoutransaction.di

import me.manulorenzo.moneyyoutransaction.data.MoshiModule
import me.manulorenzo.moneyyoutransaction.data.RepositoryImpl
import me.manulorenzo.moneyyoutransaction.ui.main.AccountViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { MoshiModule.moshiAccountAdapter }
    single { RepositoryImpl(androidApplication(), get()) }
    viewModel { AccountViewModel(get()) }
}