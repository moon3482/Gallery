package com.charlie.gallery.util

import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.underline

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