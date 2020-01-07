package me.manulorenzo.moneytransactions.shared

import me.manulorenzo.moneytransactions.data_transaction.Transaction
import me.manulorenzo.moneytransactions.data_transaction.TransactionData
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.math.BigDecimal

class ExtensionsKtTest {
    @Test
    fun `given a valid date, it should parse it and return a LocalDateTime`() {
        val uatString = "2018-05-14T08:19:00Z"
        assertEquals(
            LocalDateTime.parse(
                uatString,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
            ), uatString.toDate()
        )
    }

    @Test
    fun `given a TransactionData, it should convert it to a Transaction`() {
        val transactionData = TransactionData(
            "trx2",
            "30.00",
            "thanks for the dinner",
            "NL91ABNA0417164300",
            "2018-05-14T12:19:00Z"
        )
        val transaction = Transaction(
            amount = BigDecimal("30.00"),
            balanceBefore = BigDecimal.ZERO,
            balanceAfter = BigDecimal.ZERO,
            description = "thanks for the dinner",
            otherAccount = "NL91ABNA0417164300",
            date = "2018-05-14T12:19:00Z".toDate()
        )
        assertEquals(transaction, transactionData.transaction())
    }

    @Test
    fun `given an AccountData, it should convert it to an Account`() {
        val fakeTransactionData = TransactionData(
            "trx2",
            "30.00",
            "thanks for the dinner",
            "NL91ABNA0417164300",
            "2018-05-14T12:19:00Z"
        )
        val fakeAccountData = me.manulorenzo.moneytransactions.data_account.AccountData(
            "NL30MOYO0001234567",
            "100.20",
            listOf(
                fakeTransactionData
            )
        )
        val fakeTransaction = fakeTransactionData.transaction(
            balanceAfter = BigDecimal("130.20"),
            balanceBefore = BigDecimal("100.20")
        )
        assertEquals(
            fakeAccountData.account(BigDecimal("100.20")),
            me.manulorenzo.moneytransactions.data_account.Account(
                "NL30MOYO0001234567",
                "100.20",
                listOf(fakeTransaction)
            )
        )
    }

    @Test
    fun `given a list of Transaction and an initial balance, it should map each transaction and fill in the balance before and after`() {
        val fakeTransaction = Transaction(
            amount = BigDecimal("30.00"),
            balanceBefore = BigDecimal.ZERO,
            balanceAfter = BigDecimal.ZERO,
            description = "thanks for the dinner",
            otherAccount = "NL91ABNA0417164300",
            date = "2018-05-14T12:19:00Z".toDate()
        )
        val fakeAccountData = me.manulorenzo.moneytransactions.data_account.AccountData(
            "NL30MOYO0001234567",
            "100.20",
            listOf(
                TransactionData(
                    "trx2",
                    "30.00",
                    "thanks for the dinner",
                    "NL91ABNA0417164300",
                    "2018-05-14T12:19:00Z"
                )
            )
        ).account(BigDecimal("100.20"))
        val transactionList = fakeAccountData.transactions

        val sutList =
            listOf(fakeTransaction).map(BigDecimal("100.20")) { transaction: Transaction, beforeBalance: BigDecimal, afterBalance: BigDecimal ->
                Transaction(
                    transaction.amount,
                    beforeBalance,
                    afterBalance,
                    transaction.description,
                    transaction.otherAccount,
                    transaction.date
                )
            }
        assertEquals(transactionList, sutList)
    }
}