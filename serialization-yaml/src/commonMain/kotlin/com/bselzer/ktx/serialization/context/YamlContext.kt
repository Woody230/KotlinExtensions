package com.bselzer.ktx.serialization.context

import kotlinx.serialization.DeserializationStrategy
import net.mamoe.yamlkt.Yaml

open class YamlContext(
    val instance: Yaml
) : StringFormatContext(instance) {
    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = instance.decodeFromString(deserializer, this)

    companion object Default : YamlContext(Yaml)
}