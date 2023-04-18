package com.charlie.gallery.logic

import com.charlie.gallery.model.Size
import kotlin.math.roundToInt

object ImageCalculator {

    enum class Longer {
        WIDTH, HEIGHT
    }

    fun getCompareRatio(width: Int, height: Int, targetWidth: Int, targetHeight: Int): Size {
        return when (compareToSize(width, height)) {
            Longer.WIDTH -> {
                val ratio = getCalculateRatio(width, targetWidth.toDouble())
                Size((width * ratio).roundToInt(), (height * ratio).roundToInt())
            }
            Longer.HEIGHT -> {
                val ratio = getCalculateRatio(height, targetHeight.toDouble())
                Size((width * ratio).roundToInt(), (height * ratio).roundToInt())
            }
        }
    }

    fun compareToSize(width: Int, height: Int) =
        if (width > height) Longer.WIDTH else Longer.HEIGHT

    fun getCalculateRatio(origin: Int, target: Double): Double {
        return target / origin
    }
}