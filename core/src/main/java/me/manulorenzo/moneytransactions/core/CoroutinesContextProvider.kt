package me.manulorenzo.moneytransactions.core

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutinesContextProvider {
    open val main: CoroutineContext = Dispatchers.Main
    open val io: CoroutineContext = Dispatchers.IO
    open val default: CoroutineContext = Dispatchers.Default
}