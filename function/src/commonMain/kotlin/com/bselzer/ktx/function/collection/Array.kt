package com.bselzer.ktx.function.collection

/**
 * @return whether the item exists and is one of the items in the collection
 */
fun <T> T.isOneOf(vararg items: T?): Boolean
{
    this ?: return false
    return items.contains(this)
}