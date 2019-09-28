package me.manulorenzo.moneyyoutransaction.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.main_fragment.transactionsRecyclerView
import me.manulorenzo.moneyyoutransaction.R
import me.manulorenzo.moneyyoutransaction.model.TransactionData
import me.manulorenzo.moneyyoutransaction.model.data.Account
import me.manulorenzo.moneyyoutransaction.ui.TransactionListAdapter
import me.manulorenzo.moneyyoutransaction.util.toPresentationTransactionDataList

class MainFragment : Fragment() {
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
        viewModel.getAccount(assetManager = assets)
        viewModel.accountLiveData.observe(this, Observer { account: Account ->
            val transactionDataList = account.transactions.toPresentationTransactionDataList()
                .sortedBy { transactionData: TransactionData -> transactionData.date }
            val adapter = TransactionListAdapter(transactionDataList, clickListener = {
                // TODO Go to new fragment/activity
            })
            transactionsRecyclerView.adapter = adapter
        })
    }
}
