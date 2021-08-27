package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mohammadosman.structuredconcurrency_kotlincoroutines.databinding.ActivityMainBinding
import com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation.common.DispatcherProviderImpl
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

class MainActivity : AppCompatActivity() {
    companion object {
        private const val Ui_State = "uiState"
    }

    private lateinit var binding: ActivityMainBinding

    private val viewmodel = MainViewModel(
        DispatcherProviderImpl()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * This block will trigger when viewModel will be active state
         * when onCreate() gets Called.
         * */
        if(viewmodel.isActive) {
            Log.d(TAG,"onCreate: ViewModel is Active Now.")
        }
        initObserver()
        fetchData()
    }

    private fun fetchData() {
        binding.btnFetch.setOnClickListener {
            viewmodel.fetch()
        }
    }

    private fun initObserver() {
        viewmodel.state.observe(this, {
            it?.let { state ->
                progressBar(state.progressBar)
                binding.txtViewData1.text = state.post
                binding.txtViewData2.text = state.user
                state.errorMsg?.let { msg ->
                    toast(msg)
                }
            }
        })

    }

    private fun progressBar(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    /**
     * Since we are not using actual ViewModel() we have to persist the data
     * across the configuration changes with classic mechanism and bonus point is
     * we are also persisting it when Process Death occurs
    */
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
        // cancelling the scope or viewmodel(in this usecase) when onDestroy triggers.
        viewmodel.cancel()
        /**
         * When we rotate the device viewmodel will no longer in active state
         * then this block will trigger.
         * */
        if(!viewmodel.isActive) {
            Log.d(TAG,"onDestroy: ViewModel is No Longer Active.")
        }
    }
}