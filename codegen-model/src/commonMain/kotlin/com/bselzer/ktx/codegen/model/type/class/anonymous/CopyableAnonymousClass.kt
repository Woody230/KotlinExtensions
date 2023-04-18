package com.bselzer.ktx.codegen.model.type.`class`.anonymous

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.`super`.`class`.SuperClass
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.DelegableSuperInterface

data class CopyableAnonymousClass(
    override val superClass: SuperClass?,
    override val superInterfaces: Collection<DelegableSuperInterface> = emptyList(),
    override val initializer: CodeBlock? = null,
    override val properties: Collection<Property> = emptyList(),
    override val functions: Collection<Function> = emptyList(),
    override val documentation: Documentation? = null,
    override val annotations: Collection<Annotation> = emptyList(),
) : AnonymousClass