package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.ui.Modifier

interface Modifiable {
    /**
     * The modifier associated with the [Modifiable] properties.
     */
    val modifier: Modifier
}

/**
 * Concatenates this modifier with another.
 * Returns a Modifier representing this modifier followed by other in sequence.
 *
 * If the [other] is null, then nothing is applied.
 */
infix fun Modifier.then(other: Modifiable?) = other?.let { then(other.modifier) } ?: this