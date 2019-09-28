package me.manulorenzo.moneyyoutransaction.ui.main

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.manulorenzo.moneyyoutransaction.model.data.Account
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MainViewModel : ViewModel() {
    val accountLiveData: LiveData<Account>
        get() = _accountLiveData
    private val _accountLiveData: MutableLiveData<Account> = MutableLiveData()

    fun getAccount(assetManager: AssetManager?) {
        val transactionString = retrieveTransactionString(assetManager)
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Account> = moshi.adapter(Account::class.java)
        _accountLiveData.postValue(jsonAdapter.fromJson(transactionString))
    }

    private fun retrieveTransactionString(assetManager: AssetManager?): String {
        val filename = "transactions.json"
        val inputStream: InputStream? = assetManager?.open(filename, AssetManager.ACCESS_BUFFER)
        val outputStream = ByteArrayOutputStream()
        inputStream.use { input ->
            outputStream.use { output ->
                input?.copyTo(output)
            }
        }
        val byteArray = outputStream.toByteArray()
        return String(byteArray, Charsets.UTF_8)
    }
}
