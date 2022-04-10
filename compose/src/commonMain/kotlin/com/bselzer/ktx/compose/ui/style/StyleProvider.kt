package com.bselzer.ktx.compose.ui.style

import androidx.compose.runtime.*

/**
 * A [ProvidableCompositionLocal] wrapper for [Styleable]s.
 */
class StyleProvider<T>(@PublishedApi internal val provider: ProvidableCompositionLocal<T>) where T : Styleable<T> {
    /**
     * The nearest composition value that is localized with other composition styles.
     */
    inline val current: T
        @Composable
        get() = provider.current.localized()

    /**
     * Associates a [CompositionLocal] key to a value in a call to [CompositionLocalProvider].
     *
     * @see CompositionLocal
     * @see ProvidableCompositionLocal
     */
    infix fun provides(value: T): ProvidedValue<T> = provider.provides(value)

    /**
     * Associates a [CompositionLocal] key to a value in a call to [CompositionLocalProvider] if the key does not
     * already have an associated value.
     *
     * @see CompositionLocal
     * @see ProvidableCompositionLocal
     */
    infix fun providesDefault(value: T): ProvidedValue<T> = provider.providesDefault(value)
}