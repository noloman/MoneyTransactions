package me.manulorenzo.moneytransactions.data.repository

import android.content.Context
import com.squareup.moshi.JsonAdapter
import me.manulorenzo.moneytransactions.data.model.AccountData
import java.io.InputStream

class RepositoryImpl(
    private val context: Context,
    private val moshiJsonAccountDataAdapter: JsonAdapter<AccountData>
) : Repository {
    override suspend fun getAccount(): AccountData? =
        moshiJsonAccountDataAdapter.fromJson(getTransactionString())

    override suspend fun getTransactionString(fileName: String): String {
        val inputStream: InputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}