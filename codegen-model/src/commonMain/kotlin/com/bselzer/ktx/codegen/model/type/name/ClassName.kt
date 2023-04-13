package com.bselzer.ktx.codegen.model.type.name

data class ClassName(
    val packageName: String,
    val className: String,
    val nullable: Boolean = false
) : TypeName {
    override fun toString(): String = buildString {
        append(packageName)
        append('.')
        append(className)

        if (nullable) {
            append('?')
        }
    }

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

        internal val SERIALIZABLE: ClassName = ClassName("kotlinx.serialization", "Serializable")
    }
}