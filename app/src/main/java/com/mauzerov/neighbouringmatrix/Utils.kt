package com.mauzerov.neighbouringmatrix

import androidx.annotation.ColorInt

@ColorInt
fun argbToRgba(@ColorInt color: Int): Int {
    val a = color shr 24 and 0xff
    val r = color shr 16 and 0xff
    val g = color shr 8 and 0xff
    val b = color and 0xff
    return a or (b shl 8) or (g shl 16) or (r shl 24)
}