package com.bselzer.ktx.compose.ui.layout.merge

import com.bselzer.ktx.function.objects.safeTake

interface ComponentMerger<T> {
    /**
     * The default value.
     */
    val default: T

    /**
     * Takes the [first] object if it is not the [default], otherwise takes this object.
     */
    fun safeTake(first: T, second: T) = first.safeTake(second, default)

    /**
     * Takes the [first] object if it is not the [default], otherwise takes this object.
     */
    fun nullTake(first: T?, second: T?) = first.safeTake(second, default)
}