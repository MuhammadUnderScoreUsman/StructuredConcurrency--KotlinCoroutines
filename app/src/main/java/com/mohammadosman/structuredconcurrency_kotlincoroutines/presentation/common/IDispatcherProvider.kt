package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation.common

import kotlin.coroutines.CoroutineContext

interface IDispatcherProvider {
    fun default(): CoroutineContext
    fun io(): CoroutineContext
    fun main(): CoroutineContext
    fun unconfined(): CoroutineContext

}