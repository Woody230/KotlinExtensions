package com.bselzer.ktx.compose.ui.style

import androidx.compose.ui.Modifier

interface ModifierStyleable<T> : Styleable<T> where T : Styleable<T> {
    /**
     * Modifier to apply to the layout node.
     */
    val modifier: Modifier

    /**
     * Applies the [other] modifier after the current modifier.
     */
    infix fun with(other: Modifier): T

    /**
     * Applies the [other] modifier before the current modifier.
     */
    infix fun prioritize(other: Modifier): T
}

abstract class ModifierStyle<T> : Style<T>(), ModifierStyleable<T> where T : ModifierStyle<T> {
    /**
     * Creates a copy of the current style with the given [modifier].
     */
    protected abstract fun modify(modifier: Modifier): T

    override fun with(other: Modifier): T = modify(modifier.then(other))
    override fun prioritize(other: Modifier): T = modify(other.then(modifier))
}