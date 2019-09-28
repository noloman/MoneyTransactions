package me.manulorenzo.moneyyoutransaction.util

import me.manulorenzo.moneyyoutransaction.model.TransactionData
import me.manulorenzo.moneyyoutransaction.model.data.Transaction

fun List<Transaction>.toPresentationTransactionDataList() = map { transaction: Transaction ->
    TransactionData(
        transaction.amount,
        transaction.description,
        transaction.otherAccount,
        date = DateConverter().toDate(transaction.date)
    )
}