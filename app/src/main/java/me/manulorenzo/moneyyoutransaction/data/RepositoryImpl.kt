package me.manulorenzo.moneyyoutransaction.data

import android.app.Application
import android.content.res.AssetManager
import com.squareup.moshi.JsonAdapter
import me.manulorenzo.moneyyoutransaction.model.data.Account
import java.io.ByteArrayOutputStream
import java.io.InputStream

class RepositoryImpl(
    private val application: Application,
    private val moshiJsonAccountAdapter: JsonAdapter<Account>
) : Repository {
    override fun getAccount(): Account? = moshiJsonAccountAdapter.fromJson(getTransactionString())

    private fun getTransactionString(): String {
        val filename = "transactions.json"
        val inputStream: InputStream? =
            application.assets.open(filename, AssetManager.ACCESS_BUFFER)
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