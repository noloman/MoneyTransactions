package me.manulorenzo.moneytransactions.account

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.moneytransactions.account.util.EspressoMatchers
import me.manulorenzo.moneytransactions.data_account.Account
import me.manulorenzo.moneytransactions.data_account.AccountData
import me.manulorenzo.moneytransactions.data_transaction.TransactionData
import me.manulorenzo.moneytransactions.repository.Repository
import me.manulorenzo.moneytransactions.shared.account
import me.manulorenzo.moneytransactions.shared.di.coroutinesModule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import java.math.BigDecimal

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@LargeTest
class AccountFragmentTest : KoinTest {
    private val fakeAccountData = AccountData(
        balance = "100.20", account = "NL30MOYO0001234567", transactions = listOf(
            TransactionData(
                id = "trx1",
                amount = "-18.20",
                date = "2018-05-14T14:19:00Z",
                description = "pizza",
                otherAccount = "NL18ABNA0484869868"
            )
        )
    )
    @get:Rule
    val activityRule = ActivityTestRule(AccountActivity::class.java, true, false)

    @Before
    fun setup() {
        val testContext = InstrumentationRegistry.getInstrumentation().context
        val koin = koinApplication {
            androidContext(testContext)
            modules(coroutinesModule)
        }.koin
        declareMock<Repository> {
            mock {
                onBlocking { getAccount() } doAnswer {
                    fakeAccountData
                }
            }
        }
        declareMock<AccountViewModel> {
            whenever(accountLiveData).doReturn(MutableLiveData<Account?>().apply {
                postValue(fakeAccountData.account(BigDecimal(fakeAccountData.balance)))
            })
        }
    }

    @Test
    fun transactionsRecyclerView_shouldBeShown() = runBlockingTest {
        activityRule.launchActivity(null)
        onView(ViewMatchers.withId(R.id.transactionsRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun transactionsRecyclerView_shouldHaveFiveItems() = runBlockingTest {
        activityRule.launchActivity(null)
        onView(ViewMatchers.withId(R.id.transactionsRecyclerView)).check(
            matches(
                EspressoMatchers.hasItemCount(5)
            )
        )
    }
}