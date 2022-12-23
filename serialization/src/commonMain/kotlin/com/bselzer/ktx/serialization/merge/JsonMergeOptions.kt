package com.bselzer.ktx.serialization.merge

data class JsonMergeOptions(
    val arrayHandling: ArrayMergeHandling = ArrayMergeHandling.MERGE,
    val nullHandling: NullMergeHandling = NullMergeHandling.IGNORE
)