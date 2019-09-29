package me.manulorenzo.moneyyoutransaction.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.manulorenzo.moneyyoutransaction.data.model.Account
import me.manulorenzo.moneyyoutransaction.data.model.Transaction
import me.manulorenzo.moneyyoutransaction.data.model.ui.AccountEntity
import me.manulorenzo.moneyyoutransaction.data.repository.Repository
import me.manulorenzo.moneyyoutransaction.util.accountEntity
import java.math.BigDecimal
import kotlin.coroutines.CoroutineContext

class AccountViewModel(private val repositoryImpl: Repository) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val accountLiveData: MutableLiveData<AccountEntity> by lazy {
        MutableLiveData<AccountEntity>().also { liveData: MutableLiveData<AccountEntity> ->
            viewModelScope.launch(Dispatchers.IO) {
                val account: Account? = repositoryImpl.getAccount()
                val initialBalance = BigDecimal(getTransactionsSum(account))
                val accountEntity: AccountEntity? = account?.accountEntity(initialBalance)
                liveData.postValue(accountEntity)
            }
        }
    }

    @VisibleForTesting
    suspend fun getTransactionsSum(account: Account?): String? =
        withContext(Dispatchers.Default) {
            account?.transactions?.map { transaction: Transaction -> transaction.amount }
                ?.fold(account.balance) { acc, transactionAmount ->
                    val accInt: BigDecimal = BigDecimal(acc) - BigDecimal(transactionAmount)
                    val plainString = accInt.toPlainString()
                    plainString
                }
        }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
