package com.charlie.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.charlie.presentation.databinding.WidgetImageInfoBinding
import com.charlie.presentation.model.DetailUiModel
import com.charlie.presentation.ui.detail.DetailUiEvent

@BindingMethods(
    value = [
        BindingMethod(
            type = ImageInfoWidget::class,
            attribute = "bind:imageInfo",
            method = "setImageInfo",
        ),
        BindingMethod(
            type = ImageInfoWidget::class,
            attribute = "bind:onClickUrl",
            method = "setOnClickUrl",
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

    var imageInfo: DetailUiModel? = null
        get() = binding.imageData
        set(value) {
            binding.imageData = value
            field = value
        }

    var onClickUrl: DetailUiEvent? = null
        get() = binding.event
        set(value) {
            binding.event = value
            field = value
        }
}