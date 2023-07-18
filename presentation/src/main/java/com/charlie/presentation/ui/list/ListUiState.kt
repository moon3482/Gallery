package com.charlie.presentation.ui.list

sealed interface ListUiState {
    object None : ListUiState
    object Loading : ListUiState
    object Success : ListUiState
    data class Fail(val page: Int) : ListUiState
}