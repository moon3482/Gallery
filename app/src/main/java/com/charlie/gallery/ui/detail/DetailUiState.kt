package com.charlie.gallery.ui.detail

sealed interface DetailUiState {

    object None : DetailUiState
    object Loading : DetailUiState
    object Success : DetailUiState
    object Fail : DetailUiState
}
