package com.bselzer.ktx.codegen.model.type.`class`.enum

import com.bselzer.ktx.codegen.model.constructor.Constructor
import com.bselzer.ktx.codegen.model.constructor.ConstructorModifier
import com.bselzer.ktx.codegen.model.constructor.reference.ConstructorReference

interface EnumClassConstructor : Constructor {
    override val reference: ConstructorReference?
        get() = null

    override val modifiers: Set<ConstructorModifier>
        get() = emptySet()
}