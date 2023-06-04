package com.charlie.gallery.ui.list

sealed interface ListUIState {
    object Loading : ListUIState
    object Success : ListUIState
    data class Fail(val page: Int) : ListUIState
}