package com.charlie.gallery.util

import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import androidx.recyclerview.widget.RecyclerView

fun String?.toHyperLinkSpannable(): Spanned {
    return buildSpannedString {
        underline {
            append(
                this@toHyperLinkSpannable.orEmpty(),
                StyleSpan(Typeface.NORMAL),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}

inline fun RecyclerView.doOnScrollStateChanged(
    crossinline onScrollStateChanged: (RecyclerView, Int) -> Unit = { _, _ -> }
) {
    addScrollChangeListener(onScrollStateChanged = onScrollStateChanged)
}

inline fun RecyclerView.doOnScrolled(
    crossinline onScrolled: (RecyclerView, Int, Int) -> Unit = { _, _, _ -> }
) {
    addScrollChangeListener(onScrolled = onScrolled)
}

inline fun RecyclerView.addScrollChangeListener(
    crossinline onScrollStateChanged: (RecyclerView, Int) -> Unit = { _, _ -> },
    crossinline onScrolled: (RecyclerView, Int, Int) -> Unit = { _, _, _ -> }
): RecyclerView.OnScrollListener {
    val scrollChangedListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            onScrolled(recyclerView, dx, dy)
        }
    }

    addOnScrollListener(scrollChangedListener)
    return scrollChangedListener
}