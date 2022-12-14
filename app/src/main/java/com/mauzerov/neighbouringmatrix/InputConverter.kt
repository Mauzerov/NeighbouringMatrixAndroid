package com.mauzerov.neighbouringmatrix

import android.widget.EditText
import androidx.databinding.InverseMethod

object IC {
    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(value: Int?): String {
        return value?.toString().orEmpty()
    }

    @JvmStatic
    fun stringToInt(value: String): Int? {
        return value.toIntOrNull()
    }

    @InverseMethod("stringToBoolean")
    @JvmStatic
    fun booleanToString(value: Boolean): String {
        return (if (value) 1 else 0).toString()
    }

    @JvmStatic
    fun stringToBoolean(value: String): Boolean {
        return (value.toIntOrNull() ?: 0) != 0
    }
    @JvmStatic
    fun nullableToInt(v: Int?) : Int = v ?: 0

    @JvmStatic
    fun <T> nullableToFallback(v: T?, fallback: T) : T = v ?: fallback

    @JvmStatic
    fun nullableToFallback(v: Int?) : Int = nullableToFallback<Int>(v, -1)

    @JvmStatic
    fun <T> listToString(value: List<T>): String {
        return value.joinToString(separator = ", ")
    }
}