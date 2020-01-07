package me.manulorenzo.moneytransactions.account

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.manulorenzo.moneytransactions.core.Repository
import me.manulorenzo.moneytransactions.data_account.Account
import me.manulorenzo.moneytransactions.data_account.AccountData
import me.manulorenzo.moneytransactions.data_transaction.TransactionData
import me.manulorenzo.moneytransactions.shared.CoroutinesContextProvider
import me.manulorenzo.moneytransactions.shared.account
import java.math.BigDecimal

class AccountViewModel(
    val repository: Repository,
    private val coroutinesContextProvider: CoroutinesContextProvider
) : ViewModel() {
    val accountLiveData: MutableLiveData<Account?> by lazy {
        MutableLiveData<Account?>().also { liveData: MutableLiveData<Account?> ->
            viewModelScope.launch(coroutinesContextProvider.io) {
                val accountData: AccountData? = repository.getAccount()
                val initialBalance = BigDecimal(getTransactionsSum(accountData))
                val account = accountData?.account(initialBalance)
                liveData.postValue(account)
            }
        }
    }

    @VisibleForTesting
    suspend fun getTransactionsSum(accountData: AccountData?): String? =
        withContext(coroutinesContextProvider.default) {
            accountData?.transactions?.map { transactionData: TransactionData -> transactionData.amount }
                ?.fold(accountData.balance) { acc, transactionAmount ->
                    val accInt: BigDecimal = BigDecimal(acc) - BigDecimal(transactionAmount)
                    val plainString = accInt.toPlainString()
                    plainString
                }
        }
}
