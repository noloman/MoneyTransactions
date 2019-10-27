package me.manulorenzo.moneytransactions.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_account.accountBalance
import kotlinx.android.synthetic.main.fragment_account.transactionsRecyclerView
import me.manulorenzo.moneytransactions.data_transaction.Transaction
import me.manulorenzo.moneytransactions.navigation.Actions
import org.koin.androidx.viewmodel.ext.viewModel
import java.util.Currency
import java.util.Locale

class AccountFragment : Fragment() {
    private val accountViewModel: AccountViewModel by viewModel()

    companion object {
        fun newInstance() = AccountFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountViewModel.accountLiveData.observe(
            this,
            Observer { account: me.manulorenzo.moneytransactions.data_account.Account? ->
                accountBalance.text = String.format(
                    getString(R.string.current_balance_placeholder),
                    account?.balance + Currency.getInstance(Locale.getDefault()).symbol
                )
                val adapter = account?.transactions?.let { list: List<Transaction> ->
                    AccountTransactionsAdapter(
                        transactionList = list,
                        clickListener = { transaction: Transaction ->
                            activity?.startActivity(
                                Actions.openTransactionsIntent(
                                    activity as FragmentActivity,
                                    transaction
                                )
                            )
                        })
                }
                transactionsRecyclerView.adapter = adapter
            })
    }
}
