package me.manulorenzo.moneytransactions.transaction

import android.os.Bundle
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.fragment_transaction.amount
import kotlinx.android.synthetic.main.fragment_transaction.balanceAfter
import kotlinx.android.synthetic.main.fragment_transaction.balanceBefore
import kotlinx.android.synthetic.main.fragment_transaction.description
import kotlinx.android.synthetic.main.fragment_transaction.otherAccount
import me.manulorenzo.moneytransactions.data_transaction.Transaction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDateTime
import java.math.BigDecimal
import java.util.Currency
import java.util.Locale

@MediumTest
@RunWith(AndroidJUnit4::class)
class TransactionFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(TransactionActivity::class.java, true, false)

    @Test
    fun whenFragmentIsInitializedWithTransaction_itShouldShowTheFields() {
        val fakeTransaction = Transaction(
            amount = BigDecimal("100"),
            balanceBefore = BigDecimal.ZERO,
            balanceAfter = BigDecimal.ZERO,
            description = "bla",
            otherAccount = "other",
            date = LocalDateTime.of(2019, 11, 4, 12, 12, 12)
        )
        val fragmentArgs = Bundle().apply {
            putParcelable(
                TransactionFragment.TRANSACTION_PARCELABLE_KEY,
                fakeTransaction
            )
        }
        val scenario =
            launchFragment<TransactionFragment>(
                fragmentArgs
            )
        scenario.moveToState(Lifecycle.State.RESUMED)
            .onFragment { fragment: TransactionFragment ->
                assertNotNull(fragment)
                assertEquals(fakeTransaction.otherAccount, fragment.otherAccount.text)
                assertEquals(
                    fakeTransaction.amount.toPlainString() + Currency.getInstance(Locale.getDefault()).symbol,
                    fragment.amount.text
                )
                assertEquals(fakeTransaction.description, fragment.description.text)
                assertEquals(
                    String.format(
                        fragment.resources.getString(R.string.balance_before_placeholder),
                        fakeTransaction.balanceBefore.toPlainString() + Currency.getInstance(
                            Locale.getDefault()
                        ).symbol
                    ), fragment.balanceBefore.text
                )
                assertEquals(
                    String.format(
                        fragment.resources.getString(R.string.balance_after_placeholder),
                        fakeTransaction.balanceAfter.toPlainString() + Currency.getInstance(
                            Locale.getDefault()
                        ).symbol
                    ), fragment.balanceAfter.text
                )
            }
    }
}