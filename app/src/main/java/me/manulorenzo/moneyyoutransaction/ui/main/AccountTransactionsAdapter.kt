package me.manulorenzo.moneyyoutransaction.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transaction_row.view.amount
import kotlinx.android.synthetic.main.transaction_row.view.balanceAfter
import kotlinx.android.synthetic.main.transaction_row.view.balanceBefore
import kotlinx.android.synthetic.main.transaction_row.view.date
import kotlinx.android.synthetic.main.transaction_row.view.description
import kotlinx.android.synthetic.main.transaction_row.view.otherAccount
import me.manulorenzo.moneyyoutransaction.R
import me.manulorenzo.moneyyoutransaction.data.model.ui.Transaction
import org.threeten.bp.format.DateTimeFormatter

class TransactionListAdapter(
    private val transactionList: List<Transaction>,
    val clickListener: (View) -> (Unit)
) : RecyclerView.Adapter<TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_row, parent, false)
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
    fun bindTransaction(transaction: Transaction, clickListener: (View) -> Unit) {
        with(itemView) {
            this.setOnClickListener { clickListener.invoke(it) }
            this.balanceBefore.text = String.format(
                itemView.resources.getString(R.string.balance_before),
                transaction.balanceBefore.toPlainString()
            )
            this.balanceAfter.text = String.format(
                itemView.resources.getString(R.string.balance_after),
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
