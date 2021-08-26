package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation.common

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DispatcherProviderImpl :
    IDispatcherProvider {

    override fun default(): CoroutineContext {
        return Dispatchers.Default
    }

    override fun io(): CoroutineContext {
        return Dispatchers.IO
    }

    override fun main(): CoroutineContext {
        return Dispatchers.Main
    }

    override fun unconfined(): CoroutineContext {
        return Dispatchers.Unconfined
    }
}