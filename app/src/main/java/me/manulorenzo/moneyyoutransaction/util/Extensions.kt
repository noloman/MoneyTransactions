package me.manulorenzo.moneyyoutransaction.util

import me.manulorenzo.moneyyoutransaction.data.model.Account
import me.manulorenzo.moneyyoutransaction.data.model.Transaction
import me.manulorenzo.moneyyoutransaction.data.model.ui.AccountEntity
import me.manulorenzo.moneyyoutransaction.data.model.ui.TransactionEntity
import java.math.BigDecimal

fun Transaction.transactionEntity() =
    TransactionEntity(
        BigDecimal(this.amount),
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        this.description,
        this.otherAccount,
        date = DateConverter().toDate(this.date)
    )

inline fun <R> List<TransactionEntity>.map(
    initial: BigDecimal,
    transform: (transaction: TransactionEntity, beforeBalance: BigDecimal, afterBalance: BigDecimal) -> R
): List<R> {
    var accumulator: BigDecimal = initial
    val destination = ArrayList<R>()

    for (transaction: TransactionEntity in this) {
        val after: BigDecimal = accumulator + transaction.amount
        destination.add(transform(transaction, accumulator, after))
        accumulator = after
    }
    return destination
}

fun Account.accountEntity(initialBalance: BigDecimal): AccountEntity = AccountEntity(
    account = account,
    balance = balance,
    transactions = transactions.map { transaction: Transaction -> transaction.transactionEntity() }
        .sortedByDescending { transactionEntity: TransactionEntity -> transactionEntity.date }
        .map(initialBalance) { transaction: TransactionEntity, beforeBalance: BigDecimal, afterBalance: BigDecimal ->
            TransactionEntity(
                transaction.amount,
                beforeBalance,
                afterBalance,
                transaction.description,
                transaction.otherAccount,
                transaction.date
            )
        })