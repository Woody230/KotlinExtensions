package com.bselzer.ktx.value.identifier

interface LongIdentifier : NumberIdentifier<Long> {
    override val isDefault: Boolean
        get() = value <= 0

    override operator fun compareTo(other: Byte): Int = value.compareTo(other)
    override operator fun compareTo(other: Short): Int = value.compareTo(other)
    override operator fun compareTo(other: Int): Int = value.compareTo(other)
    override operator fun compareTo(other: Long): Int = value.compareTo(other)
    override operator fun compareTo(other: Float): Int = value.compareTo(other)
    override operator fun compareTo(other: Double): Int = value.compareTo(other)
    override operator fun compareTo(other: Identifier<Long>): Int = value.compareTo(other.value)
}