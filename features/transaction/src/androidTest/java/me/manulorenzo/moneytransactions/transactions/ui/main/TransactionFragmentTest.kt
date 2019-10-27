package me.manulorenzo.moneytransactions.transactions.ui.main

import android.os.Bundle
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import me.manulorenzo.main.MainActivity
import me.manulorenzo.moneytransactions.R
import me.manulorenzo.moneytransactions.data_transaction.Transaction
import me.manulorenzo.moneytransactions.transactions.R
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.threeten.bp.LocalDateTime
import java.math.BigDecimal
import java.util.Currency
import java.util.Locale

@RunWith(AndroidJUnit4::class)
@LargeTest
class TransactionFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)
    private val dataModule = module {
        viewModel { me.manulorenzo.moneytransactions.account.AccountViewModel(get(), get()) }
    }
    private val coroutinesModule = module {
        single { util.CoroutineContextProvider() }
    }

    @Before
    fun setup() {
        loadKoinModules(listOf(dataModule, coroutinesModule))
    }

    @Test
    fun whenFragmentIsInitializedWithTransaction_itShouldShowTheFields() {
        val localDateTimeNow = LocalDateTime.now()
        val fakeTransaction = Transaction(
            amount = BigDecimal("100"),
            balanceBefore = BigDecimal.ZERO,
            balanceAfter = BigDecimal.ZERO,
            description = "bla",
            otherAccount = "other",
            date = localDateTimeNow
        )
        val fragmentArgs = Bundle().apply {
            putParcelable(
                me.manulorenzo.moneytransactions.transactions.TransactionFragment.TRANSACTION_PARCELABLE_KEY,
                fakeTransaction
            )
        }
        val scenario =
            launchFragment<me.manulorenzo.moneytransactions.transactions.TransactionFragment>(
                fragmentArgs
            )
        scenario.moveToState(Lifecycle.State.RESUMED)
            .onFragment { fragment: me.manulorenzo.moneytransactions.transactions.TransactionFragment ->
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

    @After
    fun tearDown() {
        unloadKoinModules(listOf(dataModule, coroutinesModule))
    }
}