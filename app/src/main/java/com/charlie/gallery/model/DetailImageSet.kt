package com.charlie.gallery.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailImageSet(
    val previousImageData: ImageData? = null,
    val currentImageData: ImageData? = null,
    val nextImageData: ImageData? = null,
) : Parcelable
