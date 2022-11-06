package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiMap(val value: Map<String, OpenApiValue>) : OpenApiValue, Map<String, OpenApiValue> {
    override val entries: Set<Map.Entry<String, OpenApiValue>>
        get() = value.entries
    override val keys: Set<String>
        get() = value.keys
    override val size: Int
        get() = value.size
    override val values: Collection<OpenApiValue>
        get() = value.values

    override fun containsKey(key: String): Boolean = value.containsKey(key)
    override fun containsValue(value: OpenApiValue): Boolean = this.value.containsValue(value)
    override fun get(key: String): OpenApiValue? = value[key]
    override fun isEmpty(): Boolean = value.isEmpty()
}