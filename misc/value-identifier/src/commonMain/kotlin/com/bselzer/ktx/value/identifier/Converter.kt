package com.bselzer.ktx.value.identifier

/**
 * Creates a [ByteIdentifier] with this [Byte] as the value.
 */
fun Byte.identifier() = object : ByteIdentifier {
    override val value: Byte = this@identifier
    override fun toString(): String = value.toString()
}

/**
 * Creates a [DoubleIdentifier] with this [Double] as the value.
 */
fun Double.identifier() = object : DoubleIdentifier {
    override val value: Double = this@identifier
    override fun toString(): String = value.toString()
}

/**
 * Creates a [FloatIdentifier] with this [Float] as the value.
 */
fun Float.identifier() = object : FloatIdentifier {
    override val value: Float = this@identifier
    override fun toString(): String = value.toString()
}

/**
 * Creates a [IntIdentifier] with this [Int] as the value.
 */
fun Int.identifier() = object : IntIdentifier {
    override val value: Int = this@identifier
    override fun toString(): String = value.toString()
}

/**
 * Creates a [DoubleIdentifier] with this [Double] as the value.
 */
fun Long.identifier() = object : LongIdentifier {
    override val value: Long = this@identifier
    override fun toString(): String = value.toString()
}

/**
 * Creates a [ShortIdentifier] with this [Short] as the value.
 */
fun Short.identifier() = object : ShortIdentifier {
    override val value: Short = this@identifier
    override fun toString(): String = value.toString()
}

/**
 * Creates a [StringIdentifier] with this [String] as the value.
 */
fun String.identifier() = object : StringIdentifier {
    override val value: String = this@identifier
    override fun toString(): String = value
}
