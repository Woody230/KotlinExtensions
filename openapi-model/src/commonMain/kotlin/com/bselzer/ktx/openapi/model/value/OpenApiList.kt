package com.bselzer.ktx.openapi.model.value

import kotlin.jvm.JvmInline

@JvmInline
value class OpenApiList(val value: List<OpenApiValue>) : OpenApiValue, List<OpenApiValue> {
    override val size: Int
        get() = value.size

    override fun contains(element: OpenApiValue): Boolean = value.contains(element)
    override fun containsAll(elements: Collection<OpenApiValue>): Boolean = value.containsAll(elements)
    override fun get(index: Int): OpenApiValue = value[index]
    override fun indexOf(element: OpenApiValue): Int = value.indexOf(element)
    override fun isEmpty(): Boolean = value.isEmpty()
    override fun iterator(): Iterator<OpenApiValue> = value.iterator()
    override fun lastIndexOf(element: OpenApiValue): Int = value.lastIndexOf(element)
    override fun listIterator(): ListIterator<OpenApiValue> = value.listIterator()
    override fun listIterator(index: Int): ListIterator<OpenApiValue> = value.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): List<OpenApiValue> = value.subList(fromIndex, toIndex)
}