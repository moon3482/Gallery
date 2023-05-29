package com.charlie.gallery.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.charlie.gallery.databinding.WidgetLabelBinding
import com.charlie.gallery.util.toHyperLinkSpannable

@BindingMethods(
    value = [
        BindingMethod(
            type = LabelWidget::class,
            attribute = "android:label",
            method = "setLabel",
        ),
        BindingMethod(
            type = LabelWidget::class,
            attribute = "android:content",
            method = "setContent",
        ),
        BindingMethod(
            type = LabelWidget::class,
            attribute = "android:urlLink",
            method = "setUrlLink",
        ),
        BindingMethod(
            type = LabelWidget::class,
            attribute = "android:moveWebView",
            method = "setMoveWebView",
        ),
    ]
)

class LabelWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = WidgetLabelBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setLabel(label: String?) {
        binding.labelText.text = label
    }

    fun setContent(content: String?) {
        binding.contentText.text = content
    }

    fun setUrlLink(url: String?) {
        binding.contentText.text = url.toHyperLinkSpannable()
    }
}