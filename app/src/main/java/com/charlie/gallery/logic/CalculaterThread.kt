package com.charlie.gallery.logic

import com.charlie.gallery.model.Size

class CalculatorThread(
    private val origin: Size,
    private val target: Size,
    private val callback: (Size) -> Unit
) : Thread(Runnable {
    callback(
        Calculator.getCompareRatio(
            width = origin.width,
            height = origin.height,
            targetWidth = target.width,
            targetHeight = target.height
        )
    )
})