package me.manulorenzo.moneyyoutransaction.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transaction_row.view.amount
import kotlinx.android.synthetic.main.transaction_row.view.date
import kotlinx.android.synthetic.main.transaction_row.view.description
import kotlinx.android.synthetic.main.transaction_row.view.otherAccount
import me.manulorenzo.moneyyoutransaction.R
import me.manulorenzo.moneyyoutransaction.model.TransactionData
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionListAdapter(
    private val transactionList: List<TransactionData>,
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

class TransactionViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    fun bindTransaction(transaction: TransactionData, clickListener: (View) -> Unit) {
        with(itemView) {
            //            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
//            val date: Date? = dateFormat.parse(transaction.date)
            val dateTimeFormatter =
                SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.getDefault())
            this.setOnClickListener { clickListener.invoke(it) }
            this.amount.text = transaction.amount
            // TODO Fix ""
//            this.date.text = date?.let { dateTimeFormatter.format(it) } ?: ""
            this.date.text = dateTimeFormatter.format(transaction.date)
            this.otherAccount.text = transaction.otherAccount
            this.description.text = transaction.description
        }
    }
}
