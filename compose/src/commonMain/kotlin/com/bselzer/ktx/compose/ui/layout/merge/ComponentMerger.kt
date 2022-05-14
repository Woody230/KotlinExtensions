package com.bselzer.ktx.compose.ui.layout.merge

import com.bselzer.ktx.function.objects.safeMerge

interface ComponentMerger<T> {
    /**
     * The default value.
     */
    val default: T

    /**
     * Determines whether the [value] is the exact reference of the [default].
     */
    fun isDefault(value: T) = value == default

    /**
     * Merges the [first] object with the [second] object by taking the [second] object if it is not the [default], otherwise taking the [first] object.
     */
    fun safeMerge(first: T, second: T) = first.safeMerge(second, default)

    /**
     * Merges the [first] object with the [second] object by taking the [second] object if it is not the [default], otherwise taking the [first] object.
     */
    fun nullMerge(first: T?, second: T?) = first.safeMerge(second, default)
}