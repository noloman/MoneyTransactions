package me.manulorenzo.moneytransactions.util

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
    override val default: CoroutineContext = Dispatchers.Unconfined
}