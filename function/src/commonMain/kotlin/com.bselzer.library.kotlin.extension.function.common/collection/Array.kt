package com.bselzer.library.kotlin.extension.function.common.collection

/**
 * @return whether or not the item exists and is one of the items in the collection
 */
fun <T> T.isOneOf(vararg items: T?): Boolean
{
    this ?: return false
    return items.contains(this)
}