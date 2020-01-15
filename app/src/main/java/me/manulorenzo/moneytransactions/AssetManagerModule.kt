package me.manulorenzo.moneytransactions

import org.koin.core.module.Module
import org.koin.dsl.module

fun assetManagerModule(moneyTransactionsApplication: MoneyTransactionsApplication): Module =
    module {
        single {
            moneyTransactionsApplication.assets
        }
    }