package com.bselzer.ktx.openapi.client.model.enum

import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass

interface EnumResolver {
    fun canResolve(context: EnumContext): Boolean
    fun resolve(context: EnumContext): EnumClass
}