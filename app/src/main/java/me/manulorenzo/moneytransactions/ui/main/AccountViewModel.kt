package me.manulorenzo.moneytransactions.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.manulorenzo.moneytransactions.data.model.AccountData
import me.manulorenzo.moneytransactions.data.model.TransactionData
import me.manulorenzo.moneytransactions.data.model.ui.Account
import me.manulorenzo.moneytransactions.data.repository.Repository
import me.manulorenzo.moneytransactions.util.CoroutineContextProvider
import me.manulorenzo.moneytransactions.util.account
import java.math.BigDecimal

class AccountViewModel(
    val repository: Repository,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {
    val accountLiveData: MutableLiveData<Account> by lazy {
        MutableLiveData<Account>().also { liveData: MutableLiveData<Account> ->
            viewModelScope.launch(coroutineContextProvider.io) {
                val accountData: AccountData? = repository.getAccount()
                val initialBalance = BigDecimal(getTransactionsSum(accountData))
                val account = accountData?.account(initialBalance)
                liveData.postValue(account)
            }
        }
    }

    @VisibleForTesting
    suspend fun getTransactionsSum(accountData: AccountData?): String? =
        withContext(coroutineContextProvider.default) {
            accountData?.transactions?.map { transactionData: TransactionData -> transactionData.amount }
                ?.fold(accountData.balance) { acc, transactionAmount ->
                    val accInt: BigDecimal = BigDecimal(acc) - BigDecimal(transactionAmount)
                    val plainString = accInt.toPlainString()
                    plainString
                }
        }
}
