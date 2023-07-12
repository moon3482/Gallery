package com.charlie.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.charlie.presentation.databinding.WidgetLabelBinding
import com.charlie.presentation.util.toHyperLinkSpannable

@BindingMethods(
    value = [
        BindingMethod(
            type = LabelWidget::class,
            attribute = "bind:label",
            method = "setLabel",
        ),
        BindingMethod(
            type = LabelWidget::class,
            attribute = "bind:content",
            method = "setContent",
        ),
        BindingMethod(
            type = LabelWidget::class,
            attribute = "bind:urlLink",
            method = "setUrlLink",
        ),
    ]
)

class LabelWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = WidgetLabelBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var label: String? = null
        get() = binding.labelText.text.toString()
        set(value) {
            binding.labelText.text = value
            field = value
        }

    var content: String? = null
        get() = binding.contentText.text.toString()
        set(value) {
            binding.contentText.text = value
            field = value
        }

    var urlLink: String? = null
        get() = binding.contentText.text.toString()
        set(value) {
            binding.contentText.text = value.toHyperLinkSpannable()
            field = value
        }
}