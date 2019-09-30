package me.manulorenzo.moneytransactions.data

import android.app.Application
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.moneytransactions.data.model.AccountData
import me.manulorenzo.moneytransactions.data.repository.Repository
import me.manulorenzo.moneytransactions.di.dataModule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

@ExperimentalCoroutinesApi
class RepositoryTest : AutoCloseKoinTest() {
    private val context: Application = mock()
    private val repository: Repository by inject()
    private val moshiWrapperAdapter: JsonAdapter<AccountData> by inject()
    private var fakeAccountData: AccountData? = null

    @Before
    fun setup() {
        startKoin {
            androidContext(context)
            modules(dataModule)
        }
        declareMock<Repository>()
        declareMock<JsonAdapter<AccountData>>()
    }

    @Test
    fun `it should retrieve an account from the transactions json`() = runBlockingTest {
        val json = javaClass.classLoader?.getResourceAsStream("transactions.json").toString()
        whenever(repository.getTransactionString()).doReturn(json)

        fakeAccountData = moshiWrapperAdapter.fromJson(json)
        val expectedAccount = repository.getAccount()

        assertEquals(fakeAccountData, expectedAccount)
    }
}