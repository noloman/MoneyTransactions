package me.manulorenzo.moneytransactions.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.moneytransactions.EspressoMatchers
import me.manulorenzo.moneytransactions.R
import me.manulorenzo.moneytransactions.TestCoroutineContextProvider
import me.manulorenzo.moneytransactions.data.model.ui.Account
import me.manulorenzo.moneytransactions.data.repository.Repository
import me.manulorenzo.moneytransactions.util.CoroutineContextDelegate
import me.manulorenzo.moneytransactions.util.CoroutineContextProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@LargeTest
class AccountFragmentTest {
    @Mock
    private lateinit var repositoryMock: Repository
    private lateinit var accountViewModel: AccountViewModel
    private var fakeAccount: Account? = null
    private val dataModule = module {
        viewModel { AccountViewModel(get(), get()) }
    }
    private val coroutinesModule = module {
        single { CoroutineContextProvider() }
        single { CoroutineContextDelegate() }
    }
    @get:Rule
    val activityRule = ActivityTestRule(AccountActivity::class.java, true, false)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        loadKoinModules(listOf(dataModule, coroutinesModule))
        accountViewModel =
            AccountViewModel(repositoryMock, TestCoroutineContextProvider())
        fakeAccount = Moshi.Builder().build().adapter<Account>(Account::class.java).fromJson(json)
        `when`(accountViewModel.accountLiveData).thenReturn(MutableLiveData<Account>().apply {
            value = fakeAccount
        })
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

    @After
    fun tearDown() {
        unloadKoinModules(listOf(dataModule, coroutinesModule))
    }

    private val json = """
{
    "account" : "NL30MOYO0001234567",
    "balance" : "100.20",
    "transactions" : [
        {
            "id" : "trx1",
            "amount" : "-18.20",
            "description" : "pizza",
            "otherAccount" : "NL18ABNA0484869868",
            "date" : "2018-05-14T14:19:00Z"
        },
        {
            "id" : "trx3",
            "amount" : "-7.00",
            "description" : "albert heijn to go sandwich",
            "otherAccount" : "NL40ABNA0423660144",
            "date" : "2018-05-14T08:19:00Z"
        },
        {
            "id" : "trx2",
            "amount" : "30.00",
            "description" : "thanks for the dinner",
            "otherAccount" : "NL91ABNA0417164300",
            "date" : "2018-05-14T12:19:00Z"
        },
        {
            "id" : "trx5",
            "amount" : "-4.30",
            "description" : "starbucks amsterdam CS",
            "otherAccount" : "NL36MOYO0801604079",
            "date" : "2018-05-12T13:19:00Z"
        },
        {
            "id" : "trx4",
            "amount" : "-750.00",
            "description" : "rent",
            "otherAccount" : "NL21INGB0660968215",
            "date" : "2018-05-13T18:19:00Z"
        }
    ]
}
    """.trimIndent()
}