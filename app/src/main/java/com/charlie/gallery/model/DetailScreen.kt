package com.charlie.gallery.model

sealed class DetailScreen {
    data class OnClickPrevious(val imageData: ImageData) : DetailScreen()
    data class OnClickNext(val imageData: ImageData) : DetailScreen()
}
