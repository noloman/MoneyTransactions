package me.manulorenzo.moneytransactions.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.account_transaction_row.view.amount
import kotlinx.android.synthetic.main.account_transaction_row.view.balanceAfter
import kotlinx.android.synthetic.main.account_transaction_row.view.balanceBefore
import kotlinx.android.synthetic.main.account_transaction_row.view.date
import kotlinx.android.synthetic.main.account_transaction_row.view.description
import kotlinx.android.synthetic.main.account_transaction_row.view.otherAccount
import me.manulorenzo.moneytransactions.data_transaction.Transaction
import org.threeten.bp.format.DateTimeFormatter

class AccountTransactionsAdapter(
    private val transactionList: List<Transaction>,
    private val clickListener: (Transaction) -> (Unit)
) : RecyclerView.Adapter<TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.account_transaction_row, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.bindTransaction(transaction, clickListener)
    }
}

@VisibleForTesting
open class TransactionViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    fun bindTransaction(
        transaction: Transaction,
        clickListener: (Transaction) -> Unit
    ) {
        with(itemView) {
            this.setOnClickListener { clickListener.invoke(transaction) }
            this.balanceBefore.text = String.format(
                itemView.resources.getString(R.string.balance_before_placeholder),
                transaction.balanceBefore.toPlainString()
            )
            this.balanceAfter.text = String.format(
                itemView.resources.getString(R.string.balance_after_placeholder),
                transaction.balanceAfter.toPlainString()
            )
            this.amount.text = transaction.amount.toPlainString()
            this.date.text =
                transaction.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"))
            this.otherAccount.text = transaction.otherAccount
            this.description.text = transaction.description
        }
    }
}
