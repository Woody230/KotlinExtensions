package com.bselzer.ktx.serialization.context

import kotlinx.serialization.DeserializationStrategy
import net.mamoe.yamlkt.Yaml

sealed class YamlContext(
    override val instance: Yaml
) : StringFormatContext(instance) {
    override fun <T> String.decode(deserializer: DeserializationStrategy<T>): T = with(JsonContext) { decode(deserializer) }

    companion object Default : YamlContext(Yaml)
}