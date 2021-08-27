package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohammadosman.structuredconcurrency_kotlincoroutines.framework.network.ApiInstance.instance
import com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation.common.IDispatcherProvider
import kotlinx.coroutines.*
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

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = iDispatcher.main() + handler + job

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
                    updateState(
                        reducer().copy(
                            progressBar = false,
                            errorMsg = it.localizedMessage
                        )
                    )
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
                    updateState(
                        reducer().copy(
                            progressBar = false,
                            errorMsg = it.localizedMessage
                        )
                    )
                }
            }
        }
    }

    /**
     * This is redundant function, reason is why it is still here
     * just to show case if viewModel is not exposing the
     * coroutineScope but encapsulates it,
     * Then we can cancel the coroutineScope within the viewModel like so
     * and track to the activity or fragment.
     * */
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