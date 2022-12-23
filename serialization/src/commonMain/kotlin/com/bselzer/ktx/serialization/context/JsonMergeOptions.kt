package com.bselzer.ktx.serialization.context

data class JsonMergeOptions(
    val arrayHandling: ArrayMergeHandling = ArrayMergeHandling.MERGE
)