package me.manulorenzo.moneyyoutransaction.util

import me.manulorenzo.moneyyoutransaction.data.model.AccountData
import me.manulorenzo.moneyyoutransaction.data.model.TransactionData
import me.manulorenzo.moneyyoutransaction.data.model.ui.Account
import me.manulorenzo.moneyyoutransaction.data.model.ui.Transaction
import java.math.BigDecimal

fun TransactionData.transactionEntity() =
    Transaction(
        BigDecimal(this.amount),
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        this.description,
        this.otherAccount,
        date = DateConverter().toDate(this.date)
    )

inline fun <R> List<Transaction>.map(
    initial: BigDecimal,
    transform: (transaction: Transaction, beforeBalance: BigDecimal, afterBalance: BigDecimal) -> R
): List<R> {
    var accumulator: BigDecimal = initial
    val destination = ArrayList<R>()

    for (transaction: Transaction in this) {
        val after: BigDecimal = accumulator + transaction.amount
        destination.add(transform(transaction, accumulator, after))
        accumulator = after
    }
    return destination
}

fun AccountData.accountEntity(initialBalance: BigDecimal): Account = Account(
    account = account,
    balance = balance,
    transactions = transactions.map { transactionData: TransactionData -> transactionData.transactionEntity() }
        .sortedByDescending { transaction: Transaction -> transaction.date }
        .map(initialBalance) { transaction: Transaction, beforeBalance: BigDecimal, afterBalance: BigDecimal ->
            Transaction(
                transaction.amount,
                beforeBalance,
                afterBalance,
                transaction.description,
                transaction.otherAccount,
                transaction.date
            )
        })