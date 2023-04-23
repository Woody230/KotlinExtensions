package com.bselzer.ktx.codegen.model.type.`class`.anonymous

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.`super`.`class`.SuperClass
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.DelegableSuperInterface

data class AnonymousClass(
    val superClass: SuperClass?,
    val superInterfaces: Collection<DelegableSuperInterface> = emptyList(),
    val initializer: CodeBlock? = null,
    val properties: Collection<Property> = emptyList(),
    val functions: Collection<Function> = emptyList(),
    val documentation: Documentation? = null,
    val annotations: Collection<Annotation> = emptyList(),
)