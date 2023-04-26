package com.bselzer.ktx.value.enumeration

interface BindableEnum<T> where T : Enum<T> {
    /**
     * Converts the [BindableEnum] to the [T] enumeration.
     */
    fun toEnum(): T

    /**
     * Converts the [BindableEnum] to the [T] enumeration, or null if the conversion is not possible.
     */
    fun toEnumOrNull(): T?
}