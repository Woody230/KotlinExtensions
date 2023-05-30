package com.bselzer.ktx.serialization.merge

import net.mamoe.yamlkt.YamlElement
import net.mamoe.yamlkt.YamlList
import net.mamoe.yamlkt.YamlMap
import net.mamoe.yamlkt.YamlNull
import kotlin.math.max

open class YamlMerger(
    val options: YamlMergeOptions
) {
    companion object Default : YamlMerger(YamlMergeOptions.Default)

    fun YamlElement.merge(
        other: YamlElement?,
    ): YamlElement = when {
        other is YamlNull || other == null -> mergeNull()
        this is YamlMap && other is YamlMap -> merge(other)
        this is YamlList && other is YamlList -> merge(other)
        else -> other
    }

    private fun YamlElement.mergeNull() = when (options.nullHandling) {
        NullMergeHandling.IGNORE -> this
        NullMergeHandling.MERGE -> YamlNull
    }

    fun YamlMap.merge(other: YamlMap): YamlMap {
        val keys = keys + other.keys
        val elements = mutableMapOf<YamlElement, YamlElement>()
        for (key in keys) {
            val thisValue = this[key] ?: YamlNull
            val otherValue = other[key]
            elements[key] = thisValue.merge(otherValue)
        }

        return YamlMap(elements)
    }

    fun YamlList.merge(
        other: YamlList,
    ): YamlList = when (options.arrayHandling) {
        ArrayMergeHandling.CONCAT -> concat(other)
        ArrayMergeHandling.UNION -> union(other)
        ArrayMergeHandling.REPLACE -> other
        ArrayMergeHandling.MERGE -> replaceByIndex(other)
    }

    private fun YamlList.concat(other: YamlList): YamlList {
        val elements = plus(other)
        return YamlList(elements)
    }

    private fun YamlList.union(other: YamlList): YamlList {
        val elements = plus(other).distinct()
        return YamlList(elements)
    }

    private fun YamlList.replaceByIndex(other: YamlList): YamlList {
        val elements = mutableListOf<YamlElement>()

        for (index in 0 until max(size, other.size)) {
            val thisElement = getOrNull(index) ?: YamlNull
            val otherElement = other.getOrNull(index)
            val mergedElement = thisElement.merge(otherElement)
            elements.add(mergedElement)
        }

        return YamlList(elements)
    }
}