package me.manulorenzo.moneytransactions.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class CoroutineContextDelegate : ReadOnlyProperty<Any, CoroutineContext> {
    private val job = SupervisorJob()
    override fun getValue(thisRef: Any, property: KProperty<*>): CoroutineContext =
        job + Dispatchers.Main
}