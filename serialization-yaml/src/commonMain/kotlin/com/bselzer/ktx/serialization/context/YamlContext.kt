package com.bselzer.ktx.serialization.context

import com.bselzer.ktx.serialization.merge.YamlMergeOptions
import com.bselzer.ktx.serialization.merge.YamlMerger
import kotlinx.serialization.DeserializationStrategy
import net.mamoe.yamlkt.Yaml
import net.mamoe.yamlkt.YamlElement
import net.mamoe.yamlkt.YamlList
import net.mamoe.yamlkt.YamlMap

open class YamlContext(
    val instance: Yaml
) : StringFormatContext(instance) {
    companion object Default : YamlContext(Yaml)

    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = instance.decodeFromString(deserializer, this)

    fun YamlElement.merge(
        other: YamlElement?,
        options: YamlMergeOptions = YamlMergeOptions.Default
    ): YamlElement = YamlMerger(options).run { merge(other) }

    fun YamlMap.merge(
        other: YamlMap,
        options: YamlMergeOptions = YamlMergeOptions.Default
    ): YamlMap = YamlMerger(options).run { merge(other) }

    fun YamlList.merge(
        other: YamlList,
        options: YamlMergeOptions = YamlMergeOptions.Default
    ): YamlList = YamlMerger(options).run { merge(other) }
}