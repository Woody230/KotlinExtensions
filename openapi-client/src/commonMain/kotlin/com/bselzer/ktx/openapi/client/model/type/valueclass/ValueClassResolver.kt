package com.bselzer.ktx.openapi.client.model.type.valueclass

import com.bselzer.ktx.codegen.model.type.`class`.value.ValueClass
import com.bselzer.ktx.openapi.client.model.type.TypeResolver

interface ValueClassResolver : TypeResolver {
    fun canResolve(context: ValueClassContext): Boolean
    fun resolve(context: ValueClassContext): ValueClass
}