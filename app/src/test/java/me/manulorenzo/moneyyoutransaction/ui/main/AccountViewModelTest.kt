package me.manulorenzo.moneyyoutransaction.ui.main

import com.nhaarman.mockitokotlin2.whenever
import me.manulorenzo.moneyyoutransaction.data.MoshiModule.moshiAccountAdapter
import me.manulorenzo.moneyyoutransaction.data.RepositoryImpl
import me.manulorenzo.moneyyoutransaction.di.dataModule
import me.manulorenzo.moneyyoutransaction.model.data.Account
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AccountViewModelTest : AutoCloseKoinTest() {
    private val viewModel: AccountViewModel by inject()
    private var fakeAccount: Account? = null

    @Before
    fun setup() {
        startKoin {
            modules(dataModule)
        }
        declareMock<RepositoryImpl> {
            fakeAccount = moshiAccountAdapter.fromJson(json)
            whenever(this.getAccount()).thenReturn(fakeAccount)
        }
    }

    @Test
    fun `when getting an account it should show the expected values`() {
        viewModel.accountLiveData.observeForever { account: Account ->
            assertEquals(fakeAccount?.account, account.account)
        }
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