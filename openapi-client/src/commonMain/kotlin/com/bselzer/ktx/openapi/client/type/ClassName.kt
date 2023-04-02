package com.bselzer.ktx.openapi.client.type

class ClassName(
    private val packageName: String,
    private val className: String
) : TypeName {
    override fun toString(): String = "$packageName.$className"

    companion object {
        val ANY: ClassName = ClassName("kotlin", "Any")
        val BOOLEAN: ClassName = ClassName("kotlin", "Boolean")
        val BYTE: ClassName = ClassName("kotlin", "Byte")
        val SHORT: ClassName = ClassName("kotlin", "Short")
        val INT: ClassName = ClassName("kotlin", "Int")
        val LONG: ClassName = ClassName("kotlin", "Long")
        val CHAR: ClassName = ClassName("kotlin", "Char")
        val FLOAT: ClassName = ClassName("kotlin", "Float")
        val DOUBLE: ClassName = ClassName("kotlin", "Double")
        val STRING: ClassName = ClassName("kotlin", "String")
        val NUMBER: ClassName = ClassName("kotlin", "Number")

        val LIST: ClassName = ClassName("kotlin.collections", "List")
        val MAP: ClassName = ClassName("kotlin.collections", "Map")
    }
}