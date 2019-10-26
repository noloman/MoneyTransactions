package me.manulorenzo.moneytransactions.presentation

import me.manulorenzo.moneytransactions.data_account.Account
import me.manulorenzo.moneytransactions.data_account.AccountData
import me.manulorenzo.moneytransactions.data_transaction.Transaction
import me.manulorenzo.moneytransactions.data_transaction.TransactionData
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.math.BigDecimal
import java.text.ParseException

/**
 * Extension function that acts as a mapper from an {@code AccountData} to an {@code Account}
 */
fun String.toDate(): LocalDateTime = try {
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"))
} catch (e: ParseException) {
    // TODO Error handling
    LocalDateTime.now()
}

/**
 * Extension function that acts as a mapper from an {@code TransactionData} to a {@code Transaction}
 */
fun TransactionData.transaction(
    balanceBefore: BigDecimal = BigDecimal.ZERO,
    balanceAfter: BigDecimal = BigDecimal.ZERO
) = Transaction(
    amount = BigDecimal(this.amount),
    balanceBefore = balanceBefore,
    balanceAfter = balanceAfter,
    description = this.description,
    otherAccount = this.otherAccount,
    date = this.date.toDate()
)

/**
 * This extension function will map through a list of {@code Transaction} given an initial balance as accumulator,
 * and will fill in the {@code beforeBalance} with the accumulator and {@code afterBalance} with the accumulator plus the amount of the current transaction.
 */
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

/**
 * Extension function that acts as a mapper from an {@code AccountData} to an {@code Account}
 */
fun AccountData.account(initialBalance: BigDecimal): Account =
    Account(
        account = account,
        balance = balance,
        transactions = transactions.map { transactionData: TransactionData -> transactionData.transaction() }
            .sortedByDescending { transaction: Transaction -> transaction.date }
            .map(initialBalance) { transaction: Transaction, balanceBefore: BigDecimal, balanceAfter: BigDecimal ->
                transaction.copy(balanceBefore = balanceBefore, balanceAfter = balanceAfter)
            })