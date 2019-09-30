package me.manulorenzo.moneytransactions.data.repository

import android.content.Context
import android.content.res.AssetManager
import com.squareup.moshi.JsonAdapter
import me.manulorenzo.moneytransactions.data.model.AccountData
import java.io.ByteArrayOutputStream
import java.io.InputStream

class RepositoryImpl(
    private val context: Context,
    private val moshiJsonAccountDataAdapter: JsonAdapter<AccountData>
) : Repository {
    override suspend fun getAccount(): AccountData? =
        moshiJsonAccountDataAdapter.fromJson(getTransactionString())

    override suspend fun getTransactionString(): String {
        val filename = "transactions.json"
        val inputStream: InputStream? =
            context.assets.open(filename, AssetManager.ACCESS_BUFFER)
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