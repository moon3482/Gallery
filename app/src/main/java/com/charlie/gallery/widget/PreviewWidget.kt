package com.charlie.gallery.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.bumptech.glide.Glide
import com.charlie.gallery.databinding.WidgetPreviewBinding

@BindingMethods(
    value = [
        BindingMethod(
            type = PreviewWidget::class,
            attribute = "bind:previousUrl",
            method = "setPrevious"
        ),
        BindingMethod(
            type = PreviewWidget::class,
            attribute = "bind:currentUrl",
            method = "setCurrent"
        ),
        BindingMethod(
            type = PreviewWidget::class,
            attribute = "bind:nextUrl",
            method = "setNext"
        ),
    ]
)

class PreviewWidget @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = WidgetPreviewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true,
    )

    var previous: String? = null
        set(value) {
            setImage(value, binding.previousImageView)
            field = value
        }
    var current: String? = null
        set(value) {
            setImage(value, binding.currentImageView)
            field = value
        }
    var next: String? = null
        set(value) {
            setImage(value, binding.nextImageView)
            field = value
        }

    private fun setImage(imageUrl: String?, imageView: ImageView) {
        Glide.with(imageView)
            .load(imageUrl)
            .into(imageView)
    }
}