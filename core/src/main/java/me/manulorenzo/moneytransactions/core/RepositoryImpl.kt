package me.manulorenzo.moneytransactions.core

import android.content.Context
import com.squareup.moshi.JsonAdapter
import me.manulorenzo.moneytransactions.data_account.AccountData
import java.io.InputStream

class RepositoryImpl(
    private val context: Context,
    private val moshiJsonAccountDataAdapter: JsonAdapter<AccountData>
) : me.manulorenzo.moneytransactions.core.Repository {
    override suspend fun getAccount(): AccountData? =
        moshiJsonAccountDataAdapter.fromJson(getTransactionString())

    override suspend fun getTransactionString(fileName: String): String {
        val inputStream: InputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}