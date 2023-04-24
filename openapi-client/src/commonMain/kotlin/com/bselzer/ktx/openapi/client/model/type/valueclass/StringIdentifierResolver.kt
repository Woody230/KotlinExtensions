package com.bselzer.ktx.openapi.client.model.type.valueclass

import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.codeblock.QuotedCodeBlock
import com.bselzer.ktx.codegen.model.type.name.ClassName
import com.bselzer.ktx.openapi.model.schema.OpenApiSchemaType

object StringIdentifierResolver : IdentifierResolver() {
    override val value: ClassName = ClassName.STRING
    override val formats: Collection<String?> = listOf(null)
    override val types: Collection<OpenApiSchemaType> = listOf(OpenApiSchemaType.STRING)
    override val identifier: ClassName = ClassName.STRING_IDENTIFIER
    override val defaultValue: CodeBlock = QuotedCodeBlock.Empty
}