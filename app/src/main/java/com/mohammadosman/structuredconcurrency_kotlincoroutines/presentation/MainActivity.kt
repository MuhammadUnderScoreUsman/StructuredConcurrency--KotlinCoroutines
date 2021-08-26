package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohammadosman.structuredconcurrency_kotlincoroutines.R
import com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation.common.DispatcherProviderImpl

class MainActivity : AppCompatActivity() {
    companion object {
        private const val Ui_State = "uiState"
    }

    private val viewmodel = MainViewModel(
        DispatcherProviderImpl()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val state = viewmodel.state.value
        outState.putParcelable(
            Ui_State,
            state
        )
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getParcelable<UiState>(
            Ui_State
        )?.let { state ->
            viewmodel.updateState(
                state = state
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewmodel.cancelScope()
    }
}