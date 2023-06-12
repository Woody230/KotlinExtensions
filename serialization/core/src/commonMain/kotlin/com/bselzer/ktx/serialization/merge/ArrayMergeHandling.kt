package com.bselzer.ktx.serialization.merge

enum class ArrayMergeHandling {
    /**
     * Appends the elements.
     */
    CONCAT,

    /**
     * Skips elements that already exist.
     */
    UNION,

    /**
     * Replaces the elements.
     */
    REPLACE,

    /**
     * Replaces the elements within the same index.
     */
    MERGE
}