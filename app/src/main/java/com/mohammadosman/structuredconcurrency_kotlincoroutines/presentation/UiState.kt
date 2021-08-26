package com.mohammadosman.structuredconcurrency_kotlincoroutines.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiState(
    val user: String? = null,
    val post: String? = null,
    val progressBar: Boolean = false
) : Parcelable