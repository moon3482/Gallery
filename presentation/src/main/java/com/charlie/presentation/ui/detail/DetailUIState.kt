package com.charlie.presentation.ui.detail

sealed interface DetailUIState {
    object None : DetailUIState
    object Loading : DetailUIState
    object Success : DetailUIState
    object Fail : DetailUIState
}
