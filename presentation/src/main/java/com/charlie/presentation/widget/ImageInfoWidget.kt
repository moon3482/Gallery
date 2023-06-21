package com.charlie.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.charlie.presentation.databinding.WidgetImageInfoBinding
import com.charlie.presentation.model.DetailUIModel
import com.charlie.presentation.ui.detail.DetailUIEvent

@BindingMethods(
    value = [
        BindingMethod(
            type = ImageInfoWidget::class,
            attribute = "bind:imageInfo",
            method = "setImageInfo",
        ),
        BindingMethod(
            type = ImageInfoWidget::class,
            attribute = "bind:moveWebView",
            method = "setMoveWebView",
        ),
    ]
)

class ImageInfoWidget @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = WidgetImageInfoBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    var imageInfo: DetailUIModel? = null
        get() = binding.imageData
        set(value) {
            binding.imageData = value
            field = value
        }

    var onClickUrl: DetailUIEvent? = null
        get() = binding.event
        set(value) {
            binding.event = value
            field = value
        }
}