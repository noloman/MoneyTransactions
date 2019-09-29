package me.manulorenzo.moneyyoutransaction.util

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider {
    open val main: CoroutineContext = Dispatchers.Main
    open val io: CoroutineContext = Dispatchers.IO
    open val default: CoroutineContext = Dispatchers.Default
}