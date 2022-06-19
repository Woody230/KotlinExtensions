package com.bselzer.ktx.kodein.db.value

import com.bselzer.ktx.value.identifier.Identifier
import org.kodein.db.Value
import org.kodein.db.ValueConverter

/**
 * A [ValueConverter] for using the wrapped value associated with an [Identifier].
 */
class IdentifierValueConverter : ValueConverter {
    override fun toValue(from: Any): Value? {
        if (from !is Identifier<*>) {
            return null
        }

        return when (val value = from.value) {
            is Int -> Value.of(value)
            is Long -> Value.of(value)
            is String -> Value.of(value)
            else -> null
        }
    }
}