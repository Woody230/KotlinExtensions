package com.bselzer.ktx.codegen.model.property

import com.bselzer.ktx.codegen.model.property.declaration.AccessorPropertyDelegation

/**
 * A property that uses an accessor or delegate instead of a backing field.
 */
interface AccessorProperty : Property {
    override val declaration: AccessorPropertyDelegation
}