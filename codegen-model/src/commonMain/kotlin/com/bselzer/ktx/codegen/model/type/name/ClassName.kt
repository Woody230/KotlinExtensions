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

        // ktx-value
        private const val IDENTIFIER_PACKAGE_NAME = "com.bselzer.ktx.value.identifier"
        val BYTE_IDENTIFIER = ClassName(IDENTIFIER_PACKAGE_NAME, "ByteIdentifier")
        val DOUBLE_IDENTIFIER = ClassName(IDENTIFIER_PACKAGE_NAME, "DoubleIdentifier")
        val FLOAT_IDENTIFIER = ClassName(IDENTIFIER_PACKAGE_NAME, "FloatIdentifier")
        val INT_IDENTIFIER = ClassName(IDENTIFIER_PACKAGE_NAME, "IntIdentifier")
        val LONG_IDENTIFIER = ClassName(IDENTIFIER_PACKAGE_NAME, "LongIdentifier")
        val SHORT_IDENTIFIER = ClassName(IDENTIFIER_PACKAGE_NAME, "ShortIdentifier")
        val STRING_IDENTIFIER = ClassName(IDENTIFIER_PACKAGE_NAME, "StringIdentifier")

        // Annotations
        private const val SERIALIZATION_PACKAGE_NAME = "kotlinx.serialization"
        internal val SERIALIZABLE: ClassName = ClassName(SERIALIZATION_PACKAGE_NAME, "Serializable")
        internal val SERIAL_NAME: ClassName = ClassName(SERIALIZATION_PACKAGE_NAME, "SerialName")

        private const val JVM_PACKAGE_NAME = "kotlin.jvm"
        internal val JVM_INLINE: ClassName = ClassName("kotlin.jvm", "JvmInline")
    }
}