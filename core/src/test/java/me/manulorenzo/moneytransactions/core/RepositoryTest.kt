package me.manulorenzo.moneytransactions.core

import android.app.Application
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.manulorenzo.moneytransactions.core.di.repositoryModule
import me.manulorenzo.moneytransactions.data_account.AccountData
import me.manulorenzo.moneytransactions.shared.di.moshiAccountDataAdapterModule
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
    private var fakeAccountData: AccountData? = null
    private val moshiAccountDataAdapter: JsonAdapter<AccountData> by inject()

    @Before
    fun setup() {
        startKoin {
            androidContext(context)
            modules(listOf(repositoryModule, moshiAccountDataAdapterModule))
        }
        declareMock<Repository>()
        declareMock<JsonAdapter<AccountData>>()
    }

    @Test
    fun `it should retrieve an account from the transactions json`() = runBlockingTest {
        val json = javaClass.classLoader?.getResourceAsStream("transactions.json").toString()
        whenever(repository.getTransactionString()).doReturn(json)

        fakeAccountData = moshiAccountDataAdapter.fromJson(json)
        val expectedAccount = repository.getAccount()

        assertEquals(fakeAccountData, expectedAccount)
    }
}