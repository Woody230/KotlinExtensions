package com.bselzer.ktx.openapi.client.model.type.valueclass

import com.bselzer.ktx.codegen.model.codeblock.QuotedCodeBlock
import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.parameter.Parameter
import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.SuperInterface
import com.bselzer.ktx.openapi.client.model.extensions.toDocumentation
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object StringIdentifierResolver : IdentifierResolver() {
    override fun canResolve(context: ValueClassContext): Boolean = with(context) {
        super.canResolve(context) && schema.types.contains(OpenApiSchemaType.STRING)
    }

    override fun ValueClassContext.resolve(): ValueClass = ValueClass(
        name = name,
        documentation = schema.description?.toDocumentation(),
        primaryConstructor = Constructor(
            Parameter(
                type = ClassName.STRING,
                name = "value",
                defaultValue = QuotedCodeBlock.Empty
            )
        ),
        superInterfaces = listOf(
            SuperInterface(type = ClassName.STRING_IDENTIFIER)
        ),
    )
}