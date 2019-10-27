package me.manulorenzo.moneytransactions.repository

import android.content.Context
import com.squareup.moshi.JsonAdapter
import java.io.InputStream

class RepositoryImpl(
    private val context: Context,
    private val moshiJsonAccountDataAdapter: JsonAdapter<me.manulorenzo.moneytransactions.data_account.AccountData>
) : Repository {
    override suspend fun getAccount(): me.manulorenzo.moneytransactions.data_account.AccountData? =
        moshiJsonAccountDataAdapter.fromJson(getTransactionString())

    override suspend fun getTransactionString(fileName: String): String {
        val inputStream: InputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}