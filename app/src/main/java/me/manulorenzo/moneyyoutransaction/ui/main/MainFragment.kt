package me.manulorenzo.moneyyoutransaction.ui.main

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.main_fragment.transactionsRecyclerView
import me.manulorenzo.moneyyoutransaction.R
import me.manulorenzo.moneyyoutransaction.model.TransactionData
import me.manulorenzo.moneyyoutransaction.model.data.Account
import me.manulorenzo.moneyyoutransaction.ui.TransactionListAdapter
import me.manulorenzo.moneyyoutransaction.util.toPresentationTransactionDataList
import java.io.ByteArrayOutputStream
import java.io.InputStream


class MainFragment : Fragment() {
    private var account: Account? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val assets = activity?.assets
        val filename = "transactions.json"
        val inputStream: InputStream? = assets?.open(filename, AssetManager.ACCESS_BUFFER)
        val outputStream = ByteArrayOutputStream()
        inputStream.use { input ->
            outputStream.use { output ->
                input?.copyTo(output)
            }
        }
        val byteArray = outputStream.toByteArray()
        val transactionString = String(byteArray, Charsets.UTF_8)
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Account> = moshi.adapter(
            Account::class.java
        )

        account = jsonAdapter.fromJson(transactionString)

        val transactionDataList = account?.transactions?.toPresentationTransactionDataList()
            ?.sortedBy { transactionData: TransactionData -> transactionData.date }
        val adapter = transactionDataList?.let {
            TransactionListAdapter(it, clickListener = {
                // TODO Go to new fragment/activity
            })
        }
        transactionsRecyclerView.adapter = adapter
    }
}
