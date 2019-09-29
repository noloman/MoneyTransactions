package me.manulorenzo.moneyyoutransaction.data

import android.app.Application
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.moneyyoutransaction.data.model.Account
import me.manulorenzo.moneyyoutransaction.data.repository.Repository
import me.manulorenzo.moneyyoutransaction.di.MoshiModule
import me.manulorenzo.moneyyoutransaction.di.dataModule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mockito
import java.io.BufferedReader


@ExperimentalCoroutinesApi
class RepositoryTest : AutoCloseKoinTest() {
    private val context: Application = mock()
    private val repository: Repository by inject()
    private var fakeAccount: Account? = null

    @Before
    fun setup() {
        startKoin {
            androidContext(context)
            modules(dataModule)
        }
    }

    @Test
    fun test1() = runBlockingTest {
        whenever(context.assets) doAnswer { mock() }
        whenever(context.assets.open(any())) doAnswer { mock() }
        whenever(repository.getTransactionString()) doAnswer { json }
        val bufferedReader: BufferedReader =
            Mockito.mock<BufferedReader>(BufferedReader::class.java)
        whenever<Any>(bufferedReader.readLine()).thenReturn(json)

        fakeAccount = MoshiModule.moshiAccountAdapter.fromJson(json)

        assertEquals(fakeAccount, repository.getAccount())
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