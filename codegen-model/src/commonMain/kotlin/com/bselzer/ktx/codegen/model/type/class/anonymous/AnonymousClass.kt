package com.bselzer.ktx.codegen.model.type.`class`.anonymous

import com.bselzer.ktx.codegen.model.annotation.Annotation
import com.bselzer.ktx.codegen.model.codeblock.CodeBlock
import com.bselzer.ktx.codegen.model.documentation.Documentation
import com.bselzer.ktx.codegen.model.function.Function
import com.bselzer.ktx.codegen.model.property.Property
import com.bselzer.ktx.codegen.model.type.`super`.`class`.SuperClass
import com.bselzer.ktx.codegen.model.type.`super`.`interface`.DelegableSuperInterface

interface AnonymousClass {
    val documentation: Documentation?
    val annotations: Collection<Annotation>
    val superClass: SuperClass?
    val superInterfaces: Collection<DelegableSuperInterface>
    val initializer: CodeBlock?
    val properties: Collection<Property>
    val functions: Collection<Function>
}