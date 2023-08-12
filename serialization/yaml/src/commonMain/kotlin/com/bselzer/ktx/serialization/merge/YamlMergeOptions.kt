package com.bselzer.ktx.serialization.merge

data class YamlMergeOptions(
    val arrayHandling: ArrayMergeHandling = ArrayMergeHandling.MERGE,
    val nullHandling: NullMergeHandling = NullMergeHandling.IGNORE
) {
    companion object {
        val Default: YamlMergeOptions = YamlMergeOptions()
    }
}