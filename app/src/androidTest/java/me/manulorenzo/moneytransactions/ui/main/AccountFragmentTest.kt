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
import me.manulorenzo.moneytransactions.R
import me.manulorenzo.moneytransactions.data.model.ui.Account
import me.manulorenzo.moneytransactions.data.repository.Repository
import me.manulorenzo.moneytransactions.util.CoroutineContextProvider
import me.manulorenzo.moneytransactions.util.EspressoMatchers
import me.manulorenzo.moneytransactions.util.TestCoroutinesContextProvider
import org.junit.After
import org.junit.Before
import org.junit.Ignore
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
    }
    @get:Rule
    val activityRule = ActivityTestRule(AccountActivity::class.java, true, false)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        loadKoinModules(listOf(dataModule, coroutinesModule))
        accountViewModel =
            AccountViewModel(
                repositoryMock,
                TestCoroutinesContextProvider()
            )
        val json = javaClass.classLoader?.getResourceAsStream("transactions.json").toString()
        fakeAccount = Moshi.Builder().build().adapter<Account>(Account::class.java).fromJson(json)
        `when`(accountViewModel.accountLiveData).thenReturn(MutableLiveData<Account>().apply {
            value = fakeAccount
        })
    }


    @Test
    @Ignore
    fun transactionsRecyclerView_shouldBeShown() = runBlockingTest {
        activityRule.launchActivity(null)
        onView(ViewMatchers.withId(R.id.transactionsRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore
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
}