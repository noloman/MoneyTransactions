package me.manulorenzo.moneyyoutransaction.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.main_fragment.accountBalance
import kotlinx.android.synthetic.main.main_fragment.transactionsRecyclerView
import me.manulorenzo.moneyyoutransaction.R
import me.manulorenzo.moneyyoutransaction.model.data.Account
import me.manulorenzo.moneyyoutransaction.model.ui.TransactionData
import me.manulorenzo.moneyyoutransaction.util.toPresentationTransactionDataList
import org.koin.android.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private val accountViewModel: AccountViewModel by viewModel()

    companion object {
        fun newInstance() = AccountFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountViewModel.accountLiveData.observe(this, Observer { account: Account ->
            accountBalance.text = account.balance
            val transactionDataList = account.transactions.toPresentationTransactionDataList()
                .sortedBy { transactionData: TransactionData -> transactionData.date }
            val adapter = TransactionListAdapter(
                transactionDataList,
                clickListener = {
                    // TODO Go to new fragment/activity
                })
            transactionsRecyclerView.adapter = adapter
        })
    }
}
