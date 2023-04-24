package com.bselzer.ktx.openapi.client.model.type.enum

import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.openapi.client.model.type.TypeResolver

interface EnumResolver : TypeResolver {
    fun canResolve(context: EnumContext): Boolean
    fun resolve(context: EnumContext): EnumClass
}