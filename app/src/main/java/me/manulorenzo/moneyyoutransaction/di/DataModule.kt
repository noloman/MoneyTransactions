package me.manulorenzo.moneyyoutransaction.di

import me.manulorenzo.moneyyoutransaction.data.repository.Repository
import me.manulorenzo.moneyyoutransaction.data.repository.RepositoryImpl
import me.manulorenzo.moneyyoutransaction.ui.main.AccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { MoshiModule.moshiAccountAdapter }
    single {
        RepositoryImpl(
            get(),
            get()
        ) as Repository
    }
    viewModel { AccountViewModel(get(), get()) }
}