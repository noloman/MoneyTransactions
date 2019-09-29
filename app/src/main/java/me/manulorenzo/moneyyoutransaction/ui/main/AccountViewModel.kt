package me.manulorenzo.moneyyoutransaction.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.manulorenzo.moneyyoutransaction.data.model.Account
import me.manulorenzo.moneyyoutransaction.data.model.Transaction
import me.manulorenzo.moneyyoutransaction.data.model.ui.AccountEntity
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
    val accountLiveData: MutableLiveData<AccountEntity> by lazy {
        MutableLiveData<AccountEntity>().also { liveData: MutableLiveData<AccountEntity> ->
            launch(coroutineContextProvider.io) {
                val account: Account? = repository.getAccount()
                val initialBalance = BigDecimal(getTransactionsSum(account))
                val accountEntity: AccountEntity? = account?.accountEntity(initialBalance)
                liveData.postValue(accountEntity)
            }
        }
    }

    @VisibleForTesting
    suspend fun getTransactionsSum(account: Account?): String? =
        withContext(coroutineContextProvider.default) {
            account?.transactions?.map { transaction: Transaction -> transaction.amount }
                ?.fold(account.balance) { acc, transactionAmount ->
                    val accInt: BigDecimal = BigDecimal(acc) - BigDecimal(transactionAmount)
                    val plainString = accInt.toPlainString()
                    plainString
                }
        }
}
