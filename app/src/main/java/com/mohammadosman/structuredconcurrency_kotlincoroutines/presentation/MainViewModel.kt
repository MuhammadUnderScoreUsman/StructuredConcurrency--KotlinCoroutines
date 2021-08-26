package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohammadosman.structuredconcurrency_kotlincoroutines.framework.network.instance
import com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation.common.IDispatcherProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val iDispatcher: IDispatcherProvider
) : CoroutineScope {

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        errorResponse(
            exception = throwable.localizedMessage
                ?: "Unknown error"
        )
    }

    override val coroutineContext: CoroutineContext
        get() = iDispatcher.main() + handler

    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> get() = _state

    private val apiRequest = instance

    fun fetch() {

        updateState(reducer().copy(progressBar = true))

        launch {
            val jobOne = launch {
                val post =
                    apiRequest.getPostByUserId(1)
                val data = reducer().copy(
                    post = post.title,
                    progressBar = false
                )
                updateState(data)
            }

            jobOne.invokeOnCompletion {
                it?.let {
                    errorResponse(it.localizedMessage)
                }
            }

            val jobTwo = launch {
                val user = apiRequest.getUser(1)
                val data = reducer().copy(
                    user = user.name,
                    progressBar = false
                )
                updateState(data)
            }

            jobTwo.invokeOnCompletion {
                it?.let {
                    errorResponse(it.localizedMessage)
                }
            }
        }
    }

    internal fun cancelScope() {
        this.cancel()
    }

    internal fun updateState(state: UiState) {
        _state.value = state
    }

    private fun reducer(): UiState {
        return state.value ?: UiState()
    }

    private fun errorResponse(exception: String) {
        logd("Exception: $exception")
    }
}