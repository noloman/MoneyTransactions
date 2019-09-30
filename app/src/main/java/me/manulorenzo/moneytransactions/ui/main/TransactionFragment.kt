package me.manulorenzo.moneytransactions.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_transaction.amount
import kotlinx.android.synthetic.main.fragment_transaction.balanceAfter
import kotlinx.android.synthetic.main.fragment_transaction.balanceBefore
import kotlinx.android.synthetic.main.fragment_transaction.date
import kotlinx.android.synthetic.main.fragment_transaction.description
import kotlinx.android.synthetic.main.fragment_transaction.otherAccount
import me.manulorenzo.moneytransactions.R
import me.manulorenzo.moneytransactions.data.model.ui.Transaction
import org.threeten.bp.format.DateTimeFormatter

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
        val transaction = arguments?.getParcelable<Transaction>(TRANSACTION_PARCELABLE_KEY)

        balanceBefore.text = String.format(
            resources.getString(R.string.balance_before),
            transaction?.balanceBefore?.toPlainString()
        )
        balanceAfter.text = String.format(
            resources.getString(R.string.balance_after),
            transaction?.balanceAfter?.toPlainString()
        )
        amount.text = transaction?.amount?.toPlainString()
        date.text =
            transaction?.date?.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"))
        otherAccount.text = transaction?.otherAccount
        description.text = transaction?.description
    }
}
