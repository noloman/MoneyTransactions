package me.manulorenzo.moneyyoutransaction.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.moneyyoutransaction.data.model.Account
import me.manulorenzo.moneyyoutransaction.data.model.ui.AccountEntity
import me.manulorenzo.moneyyoutransaction.data.repository.Repository
import me.manulorenzo.moneyyoutransaction.di.coroutinesModule
import me.manulorenzo.moneyyoutransaction.di.dataModule
import me.manulorenzo.moneyyoutransaction.util.CoroutinesTestRule
import me.manulorenzo.moneyyoutransaction.util.TestCoroutineContextProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class AccountViewModelTest : AutoCloseKoinTest() {
    private lateinit var accountViewModel: AccountViewModel
    private val repository: Repository by inject()
    private val moshiAccountAdapter: JsonAdapter<Account> by inject()
    private var fakeAccount: Account? = null

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        startKoin {
            modules(listOf(dataModule, coroutinesModule))
        }
        declareMock<Repository>()
    }

    @Test
    fun `when getting an account it should show the expected values`() = runBlockingTest {
        fakeAccount = moshiAccountAdapter.fromJson(json)
        whenever(repository.getAccount()).thenReturn(fakeAccount)

        accountViewModel = AccountViewModel(repository, TestCoroutineContextProvider())
        accountViewModel.accountLiveData.observeForever { account: AccountEntity ->
            assertEquals(fakeAccount?.account, account.account)
        }
        verify(accountViewModel.repository).getAccount()
    }

    @Test
    fun `when getting the total sum of all the transactions it should return the sum of them plus the account balance`() =
        runBlockingTest {
            fakeAccount = moshiAccountAdapter.fromJson(json)
            whenever(repository.getAccount()).thenReturn(fakeAccount)

            accountViewModel = AccountViewModel(repository, TestCoroutineContextProvider())
            assertEquals("849.70", accountViewModel.getTransactionsSum(fakeAccount))
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