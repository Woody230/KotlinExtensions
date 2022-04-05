package com.bselzer.ktx.compose.ui.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.Modifier

interface Style<T: Style<T>> {
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

interface ModifiableStyle<T>: Style<T> where T: Style<T> {
    /**
     * Modifier to apply to the layout node.
     */
    val modifier: Modifier
}

/**
 * Provides a merged style of currently provided value with the new [value].
 */
@Composable
fun <T: Style<T>> ProvidableCompositionLocal<Style<T>>.merge(value: T, content: @Composable () -> Unit)
        = CompositionLocalProvider(this provides current.merge(value), content = content)