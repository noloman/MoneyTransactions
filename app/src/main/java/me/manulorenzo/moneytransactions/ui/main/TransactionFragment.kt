package me.manulorenzo.moneytransactions.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_transaction.amount
import kotlinx.android.synthetic.main.fragment_transaction.balanceAfter
import kotlinx.android.synthetic.main.fragment_transaction.balanceBefore
import kotlinx.android.synthetic.main.fragment_transaction.currentAccount
import kotlinx.android.synthetic.main.fragment_transaction.date
import kotlinx.android.synthetic.main.fragment_transaction.description
import kotlinx.android.synthetic.main.fragment_transaction.otherAccount
import me.manulorenzo.moneytransactions.R
import me.manulorenzo.moneytransactions.data.model.ui.Transaction
import org.threeten.bp.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

class TransactionFragment : Fragment() {

    companion object {
        val TAG = TransactionFragment::class.java.simpleName
        const val TRANSACTION_PARCELABLE_KEY = "transaction"
        fun newInstance(transaction: Transaction) = TransactionFragment().apply {
            arguments = Bundle().apply { putParcelable(TRANSACTION_PARCELABLE_KEY, transaction) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val transaction = arguments?.getParcelable<Transaction>(TRANSACTION_PARCELABLE_KEY)
        currentAccount.text = String.format(
            resources.getString(R.string.current_account_placeholder),
            resources.getString(R.string.current_account_number)
        )
        // TODO Fix me
        otherAccount.text = String.format(
            resources.getString(R.string.destination_account_placeholder),
            transaction?.otherAccount
        )
        balanceBefore.text = String.format(
            resources.getString(R.string.balance_before_placeholder),
            transaction?.balanceBefore?.toPlainString() + Currency.getInstance(Locale.getDefault()).symbol
        )
        balanceAfter.text = String.format(
            resources.getString(R.string.balance_after_placeholder),
            transaction?.balanceAfter?.toPlainString() + Currency.getInstance(Locale.getDefault()).symbol
        )
        amount.text = String.format(
            resources.getString(R.string.balance_amount_placeholder),
            transaction?.amount?.toPlainString() + Currency.getInstance(Locale.getDefault()).symbol
        )
        date.text =
            transaction?.date?.format(DateTimeFormatter.ofPattern("EEEE dd MMM yyyy HH:mm:ss"))
        otherAccount.text = transaction?.otherAccount
        description.text = transaction?.description

        (view.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }
    }
}
