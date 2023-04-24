package com.bselzer.ktx.openapi.client.model.type.valueclass

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.SuperInterface
import com.bselzer.ktx.openapi.client.internal.ExtensionConstants
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType
import com.bselzer.ktx.serialization.context.toBooleanOrNull
import kotlinx.serialization.json.JsonPrimitive

sealed class IdentifierResolver : ValueClassResolver {
    protected abstract val value: ClassName
    protected abstract val formats: Collection<String?>
    protected abstract val types: Collection<OpenApiSchemaType>
    protected abstract val identifier: ClassName
    protected abstract val defaultValue: CodeBlock

    override fun canResolve(context: ValueClassContext): Boolean = with(context) {
        val element = schema.extensions[ExtensionConstants.IDENTIFIER]
        if (element !is JsonPrimitive || element.toBooleanOrNull() != true) {
            return@with false
        }

        val containsType = types.any(schema.types::contains)
        val containsFormat = formats.contains(schema.format)
        return containsType && containsFormat
    }

    override fun resolve(context: ValueClassContext): ValueClass = with(context) {
        ValueClass(
            name = name,
            documentation = schema.description?.toDocumentation(),
            primaryConstructor = Constructor(
                Parameter(
                    type = value,
                    name = "value",
                    defaultValue = defaultValue
                )
            ),
            superInterfaces = listOf(
                SuperInterface(type = identifier)
            ),
            functions = listOf(
                if (value == ClassName.STRING) {
                    Function.toStringOf("value".toCodeBlock())
                } else {
                    Function.toStringOf("value.toString()".toCodeBlock())
                }
            )
        )
    }
}