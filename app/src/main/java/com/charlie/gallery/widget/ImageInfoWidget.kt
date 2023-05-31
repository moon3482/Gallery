package com.charlie.gallery.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.charlie.gallery.databinding.WidgetImageInfoBinding
import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.ui.detail.DetailUIEvent

@BindingMethods(
    value = [
        BindingMethod(
            type = ImageInfoWidget::class,
            attribute = "android:imageInfo",
            method = "setImageInfo",
        ),
        BindingMethod(
            type = ImageInfoWidget::class,
            attribute = "android:moveWebView",
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

    fun setImageInfo(imageDetailData: ImageDetailData?) {
        binding.imageData = imageDetailData
    }

    fun setMoveWebView(detailUIEvent: DetailUIEvent?) {
        binding.event = detailUIEvent
    }
}