package me.manulorenzo.moneytransactions

import kotlinx.coroutines.Dispatchers
import me.manulorenzo.moneytransactions.util.CoroutineContextProvider
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
    override val default: CoroutineContext = Dispatchers.Unconfined
}