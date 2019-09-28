package me.manulorenzo.moneyyoutransaction.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.manulorenzo.moneyyoutransaction.data.RepositoryImpl
import me.manulorenzo.moneyyoutransaction.model.data.Account

class AccountViewModel(private val repositoryImpl: RepositoryImpl) : ViewModel() {
    val accountLiveData: MutableLiveData<Account> by lazy {
        return@lazy MutableLiveData<Account>().also {
            it.postValue(repositoryImpl.getAccount())
        }
    }
}
