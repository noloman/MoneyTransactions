package me.manulorenzo.moneyyoutransaction.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import me.manulorenzo.moneyyoutransaction.util.CoroutineContextDelegate
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext by CoroutineContextDelegate()

    override fun onCleared() {
        coroutineContext.cancel()
    }
}