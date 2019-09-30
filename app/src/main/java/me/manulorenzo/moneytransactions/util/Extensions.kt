package me.manulorenzo.moneytransactions.util

import me.manulorenzo.moneytransactions.data.model.AccountData
import me.manulorenzo.moneytransactions.data.model.TransactionData
import me.manulorenzo.moneytransactions.data.model.ui.Account
import me.manulorenzo.moneytransactions.data.model.ui.Transaction
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.math.BigDecimal
import java.text.ParseException

fun String.toDate(): LocalDateTime = try {
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"))
} catch (e: ParseException) {
    // TODO Error handling
    LocalDateTime.now()
}

fun TransactionData.transaction(
    balanceBefore: BigDecimal = BigDecimal.ZERO,
    balanceAfter: BigDecimal = BigDecimal.ZERO
) =
    Transaction(
        amount = BigDecimal(this.amount),
        balanceBefore = balanceBefore,
        balanceAfter = balanceAfter,
        description = this.description,
        otherAccount = this.otherAccount,
        date = this.date.toDate()
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

fun AccountData.account(initialBalance: BigDecimal): Account = Account(
    account = account,
    balance = balance,
    transactions = transactions.map { transactionData: TransactionData -> transactionData.transaction() }
        .sortedByDescending { transaction: Transaction -> transaction.date }
        .map(initialBalance) { transaction: Transaction, balanceBefore: BigDecimal, balanceAfter: BigDecimal ->
            transaction.copy(balanceBefore = balanceBefore, balanceAfter = balanceAfter)
        })