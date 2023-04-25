package com.bselzer.ktx.openapi.client.model.type.valueclass

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.extensions.toCodeBlock
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object FloatIdentifierResolver : IdentifierResolver() {
    override val value: ClassName = ClassName.FLOAT
    override val formats: Collection<String?> = setOf("float")
    override val types: Collection<OpenApiSchemaType> = listOf(OpenApiSchemaType.NUMBER)
    override val identifier: ClassName = ClassName.FLOAT_IDENTIFIER
    override val defaultValue: CodeBlock = "0f".toCodeBlock()
}