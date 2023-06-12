package com.bselzer.ktx.serialization.merge

enum class NullMergeHandling {
    /**
     * Null values are ignored during merging.
     */
    IGNORE,

    /**
     * Null values are allowed to be merged.
     */
    MERGE
}