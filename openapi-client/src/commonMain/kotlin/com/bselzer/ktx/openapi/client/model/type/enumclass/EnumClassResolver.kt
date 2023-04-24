package com.bselzer.ktx.openapi.client.model.type.enumclass

import com.bselzer.ktx.codegen.model.type.`class`.enum.EnumClass
import com.bselzer.ktx.openapi.client.model.type.TypeResolver

interface EnumClassResolver : TypeResolver {
    fun canResolve(context: EnumContext): Boolean
    fun resolve(context: EnumContext): EnumClass
}