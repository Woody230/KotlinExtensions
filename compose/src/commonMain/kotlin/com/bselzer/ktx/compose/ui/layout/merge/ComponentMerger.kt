package com.bselzer.ktx.compose.ui.layout.merge

import com.bselzer.ktx.function.objects.nonDefault

interface ComponentMerger<T> {
    /**
     * The default value.
     */
    val default: T

    /**
     * Takes the [first] object if it is not the [default], otherwise takes this object.
     */
    fun resolve(first: T, second: T) = first.nonDefault(second, default)
}