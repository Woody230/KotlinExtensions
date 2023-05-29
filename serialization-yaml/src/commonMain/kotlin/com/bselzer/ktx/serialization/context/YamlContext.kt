package com.bselzer.ktx.serialization.context

import com.bselzer.ktx.serialization.merge.ArrayMergeHandling
import com.bselzer.ktx.serialization.merge.NullMergeHandling
import com.bselzer.ktx.serialization.merge.YamlMergeOptions
import kotlinx.serialization.DeserializationStrategy
import net.mamoe.yamlkt.Yaml
import net.mamoe.yamlkt.YamlElement
import net.mamoe.yamlkt.YamlList
import net.mamoe.yamlkt.YamlMap
import net.mamoe.yamlkt.YamlNull
import kotlin.math.max

open class YamlContext(
    val instance: Yaml
) : StringFormatContext(instance) {
    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = instance.decodeFromString(deserializer, this)

    companion object Default : YamlContext(Yaml)

    fun YamlElement.merge(
        other: YamlElement?,
        options: YamlMergeOptions = YamlMergeOptions()
    ): YamlElement = when {
        other is YamlNull || other == null -> mergeNull(options)
        this is YamlMap && other is YamlMap -> merge(other, options)
        this is YamlList && other is YamlList -> merge(other, options)
        else -> other
    }

    private fun YamlElement.mergeNull(
        options: YamlMergeOptions = YamlMergeOptions.Default
    ) = when (options.nullHandling) {
        NullMergeHandling.IGNORE -> this
        NullMergeHandling.MERGE -> YamlNull
    }

    fun YamlMap.merge(
        other: YamlMap,
        options: YamlMergeOptions = YamlMergeOptions.Default
    ): YamlMap {
        val keys = keys + other.keys
        val elements = mutableMapOf<YamlElement, YamlElement>()
        for (key in keys) {
            val thisValue = this[key] ?: YamlNull
            val otherValue = other[key]
            elements[key] = thisValue.merge(otherValue, options)
        }

        return YamlMap(elements)
    }

    fun YamlList.merge(
        other: YamlList,
        options: YamlMergeOptions = YamlMergeOptions.Default
    ): YamlList = when (options.arrayHandling) {
        ArrayMergeHandling.CONCAT -> concat(other)
        ArrayMergeHandling.UNION -> union(other)
        ArrayMergeHandling.REPLACE -> other
        ArrayMergeHandling.MERGE -> replaceByIndex(other, options)
    }

    private fun YamlList.concat(other: YamlList): YamlList {
        val elements = plus(other)
        return YamlList(elements)
    }

    private fun YamlList.union(other: YamlList): YamlList {
        val elements = plus(other).distinct()
        return YamlList(elements)
    }

    private fun YamlList.replaceByIndex(
        other: YamlList,
        options: YamlMergeOptions
    ): YamlList {
        val elements = mutableListOf<YamlElement>()

        for (index in 0 until max(size, other.size)) {
            val thisElement = getOrNull(index) ?: YamlNull
            val otherElement = other.getOrNull(index)
            val mergedElement = thisElement.merge(otherElement, options)
            elements.add(mergedElement)
        }

        return YamlList(elements)
    }
}