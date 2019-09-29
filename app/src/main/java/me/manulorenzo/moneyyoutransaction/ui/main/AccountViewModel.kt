package me.manulorenzo.moneyyoutransaction.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.manulorenzo.moneyyoutransaction.data.model.AccountData
import me.manulorenzo.moneyyoutransaction.data.model.TransactionData
import me.manulorenzo.moneyyoutransaction.data.model.ui.Account
import me.manulorenzo.moneyyoutransaction.data.repository.Repository
import me.manulorenzo.moneyyoutransaction.util.CoroutineContextDelegate
import me.manulorenzo.moneyyoutransaction.util.CoroutineContextProvider
import me.manulorenzo.moneyyoutransaction.util.accountEntity
import java.math.BigDecimal
import kotlin.coroutines.CoroutineContext

class AccountViewModel(
    @VisibleForTesting val repository: Repository,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext by CoroutineContextDelegate()
    val accountLiveData: MutableLiveData<Account> by lazy {
        MutableLiveData<Account>().also { liveData: MutableLiveData<Account> ->
            launch(coroutineContextProvider.io) {
                val accountData: AccountData? = repository.getAccount()
                val initialBalance = BigDecimal(getTransactionsSum(accountData))
                val account = accountData?.accountEntity(initialBalance)
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
