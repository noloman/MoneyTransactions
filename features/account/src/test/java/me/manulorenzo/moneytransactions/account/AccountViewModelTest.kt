package me.manulorenzo.moneytransactions.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.moneytransactions.core.Repository
import me.manulorenzo.moneytransactions.core.di.repositoryModule
import me.manulorenzo.moneytransactions.data_account.Account
import me.manulorenzo.moneytransactions.data_account.AccountData
import me.manulorenzo.moneytransactions.shared.CoroutinesTestRule
import me.manulorenzo.moneytransactions.shared.TestCoroutineContextProvider
import me.manulorenzo.moneytransactions.shared.di.coroutinesModule
import me.manulorenzo.moneytransactions.shared.di.moshiAccountDataAdapterModule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

@ExperimentalCoroutinesApi
class AccountViewModelTest : AutoCloseKoinTest() {
    private lateinit var accountViewModel: AccountViewModel
    private val repository: Repository by inject()
    private val moshiAccountDataAdapter: JsonAdapter<AccountData> by inject()
    private var fakeAccountData: AccountData? = null

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        startKoin {
            modules(
                listOf(
                    repositoryModule,
                    coroutinesModule,
                    moshiAccountDataAdapterModule
                )
            )
        }
        declareMock<Repository>()
    }

    @Test
    fun `when getting an account it should show the expected values`() = runBlockingTest {
        fakeAccountData = moshiAccountDataAdapter.fromJson(json)
        whenever(repository.getAccount()).doReturn(fakeAccountData)

        accountViewModel = AccountViewModel(
            repository,
            TestCoroutineContextProvider()
        )
        accountViewModel.accountLiveData.observeForever { account: Account? ->
            assertEquals(fakeAccountData?.account, account?.account)
        }
        verify(accountViewModel.repository).getAccount()
    }

    @Test
    fun `when getting the total sum of all the transactions it should return the sum of them plus the account balance`() =
        runBlockingTest {
            fakeAccountData = moshiAccountDataAdapter.fromJson(json)
            whenever(repository.getAccount()).doReturn(fakeAccountData)

            accountViewModel = AccountViewModel(
                repository,
                TestCoroutineContextProvider()
            )
            assertEquals("849.70", accountViewModel.getTransactionsSum(fakeAccountData))
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