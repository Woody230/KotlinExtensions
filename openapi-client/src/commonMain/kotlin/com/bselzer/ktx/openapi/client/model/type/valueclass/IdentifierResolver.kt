package com.bselzer.ktx.openapi.client.model.type.valueclass

import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.openapi.client.internal.ExtensionConstants
import com.bselzer.ktx.serialization.context.toBooleanOrNull
import kotlinx.serialization.json.JsonPrimitive

sealed class IdentifierResolver : ValueClassResolver {
    override fun canResolve(context: ValueClassContext): Boolean = with(context) {
        val element = schema.extensions[ExtensionConstants.IDENTIFIER]
        if (element !is JsonPrimitive) {
            return@with false
        }

        element.toBooleanOrNull() == true
    }

    override fun resolve(context: ValueClassContext): ValueClass = with(context) {
        val valueClass = resolve()
        valueClass.copy(
            functions = valueClass.functions + Function.toStringOf("value".toCodeBlock())
        )
    }

    protected abstract fun ValueClassContext.resolve(): ValueClass
}