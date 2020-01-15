package me.manulorenzo.moneytransactions.core

import android.content.res.AssetManager
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.manulorenzo.moneytransactions.data_account.AccountData

class Repository(
    private val assetManager: AssetManager,
    private val moshiJsonAccountDataAdapter: JsonAdapter<AccountData>,
    private val coroutineIoDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getAccount(): AccountData? = withContext(coroutineIoDispatcher) {
        moshiJsonAccountDataAdapter.fromJson(getTransactionString())
    }

    suspend fun getTransactionString(fileName: String = "transactions.json"): String =
        withContext(coroutineIoDispatcher) {
            return@withContext assetManager.open(fileName).bufferedReader().use { it.readText() }
        }
}