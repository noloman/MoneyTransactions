package me.manulorenzo.moneytransactions.account

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.moneytransactions.account.util.EspressoMatchers
import me.manulorenzo.moneytransactions.core.Repository
import me.manulorenzo.moneytransactions.data_account.Account
import me.manulorenzo.moneytransactions.data_account.AccountData
import me.manulorenzo.moneytransactions.data_transaction.TransactionData
import me.manulorenzo.moneytransactions.shared.account
import me.manulorenzo.moneytransactions.shared.di.coroutinesModule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.math.BigDecimal

@MediumTest
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
        val mockedAccountViewModelModule = module {
            factory(override = true) {
                mock<AccountViewModel> {
                    whenever(mock.accountLiveData).thenReturn(MutableLiveData<Account?>().apply {
                        postValue(fakeAccountData.account(BigDecimal(fakeAccountData.balance)))
                    })
                }
            }
        }
        val mockedRepositoryModule = module {
            factory(override = true) {
                mock<Repository> {
                    onBlocking { getAccount() } doAnswer {
                        fakeAccountData
                    }
                }
            }
        }
        if (GlobalContext.getOrNull() != null) {
            stopKoin()
        }
        startKoin {
            loadKoinModules(
                listOf(
                    coroutinesModule,
                    mockedRepositoryModule,
                    mockedAccountViewModelModule
                )
            )
        }
    }

    @Test
    fun transactionsRecyclerView_shouldBeShown() = runBlockingTest {
        launchFragmentInContainer<AccountFragment>(null, R.style.AppTheme)

        onView(ViewMatchers.withId(R.id.transactionsRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun transactionsRecyclerView_shouldHaveOnlyOneItem() = runBlockingTest {
        launchFragmentInContainer<AccountFragment>(null, R.style.AppTheme)

        onView(ViewMatchers.withId(R.id.transactionsRecyclerView)).check(
            matches(
                EspressoMatchers.hasItemCount(1)
            )
        )
    }
}