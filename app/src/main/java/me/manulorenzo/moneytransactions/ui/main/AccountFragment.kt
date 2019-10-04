package me.manulorenzo.moneytransactions.ui.main

import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_account.accountBalance
import kotlinx.android.synthetic.main.fragment_account.transactionsRecyclerView
import kotlinx.android.synthetic.main.fragment_transaction.balanceAfter
import kotlinx.android.synthetic.main.fragment_transaction.currentAccount
import kotlinx.android.synthetic.main.fragment_transaction.description
import kotlinx.android.synthetic.main.transaction_row.balanceBefore
import kotlinx.android.synthetic.main.transaction_row.date
import kotlinx.android.synthetic.main.transaction_row.otherAccount
import me.manulorenzo.moneytransactions.R
import me.manulorenzo.moneytransactions.data.model.ui.Account
import me.manulorenzo.moneytransactions.data.model.ui.Transaction
import org.koin.androidx.viewmodel.ext.android.viewModel
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
        accountViewModel.accountLiveData.observe(this, Observer { account: Account? ->
            accountBalance.text = String.format(
                getString(R.string.current_balance_placeholder),
                account?.balance + Currency.getInstance(Locale.getDefault()).symbol
            )
            val adapter = account?.transactions?.let { list: List<Transaction> ->
                AccountTransactionsAdapter(
                    transactionList = list,
                    clickListener = { transaction: Transaction ->
                        createSharedElementTransition(transaction)
                    })
            }
            transactionsRecyclerView.adapter = adapter
        })
    }

    /**
     * Creates enter and exit fragment transition and commits the fragment transaction.
     */
    private fun createSharedElementTransition(transaction: Transaction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val autoTransition =
                TransitionInflater.from(activity).inflateTransition(android.R.transition.fade)
            val slideRight: Transition = TransitionInflater.from(activity)
                .inflateTransition(android.R.transition.slide_right)
            val slideLeft: Transition = TransitionInflater.from(activity)
                .inflateTransition(android.R.transition.slide_left)
            this.sharedElementReturnTransition = autoTransition
            this.exitTransition = slideLeft

            TransactionFragment.newInstance(transaction)
                .also { transactionFragment: TransactionFragment ->
                    transactionFragment.sharedElementEnterTransition =
                        autoTransition
                    transactionFragment.enterTransition = slideRight
                    fragmentManager?.beginTransaction()
                        ?.setReorderingAllowed(true)
                        ?.replace(
                            R.id.container, transactionFragment, TransactionFragment.TAG
                        )?.addToBackStack(null)
                        ?.addSharedElement(
                            description,
                            resources.getString(R.string.description_shared_element_transition_name)
                        )
                        ?.addSharedElement(
                            otherAccount,
                            resources.getString(R.string.other_account_shared_element_transition_name)
                        )
                        ?.addSharedElement(
                            currentAccount,
                            resources.getString(R.string.current_account_shared_element_transition_name)
                        )
                        ?.addSharedElement(
                            date,
                            resources.getString(R.string.date_shared_element_transition_name)
                        )
                        ?.addSharedElement(
                            balanceBefore,
                            resources.getString(R.string.balance_before_shared_element_transition_name)
                        )
                        ?.addSharedElement(
                            balanceAfter,
                            resources.getString(R.string.balance_after_shared_element_transition_name)
                        )
                        ?.commit()
                }
        }
    }
}
