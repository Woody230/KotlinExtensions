package com.bselzer.ktx.compose.ui.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

interface Styleable<T : Styleable<T>> {
    /**
     * Returns a new style that is a combination of this style and the given [other] style.
     *
     * [other] style's null or inherit properties are replaced with the non-null properties of this text style.
     * Another way to think of it is that the "missing" properties of the [other] style are filled by the properties of this style
     *
     * If the given style is null, returns this text style.
     */
    fun merge(other: T?): T

    /**
     * Creates a localized version of this style using the [CompositionLocalProvider].
     */
    @Composable
    fun localized(): T
}

@Suppress("UNCHECKED_CAST")
abstract class Style<T> : Styleable<T> where T : Style<T> {
    protected abstract fun safeMerge(other: T): T

    @Composable
    override fun localized(): T = this as T

    override fun merge(other: T?): T = if (other == null || other === this) this as T else safeMerge(other)
}