package com.charlie.presentation.ui.list

sealed interface ListUIState {
    object None : ListUIState
    object Loading : ListUIState
    object Success : ListUIState
    data class Fail(val page: Int) : ListUIState
}