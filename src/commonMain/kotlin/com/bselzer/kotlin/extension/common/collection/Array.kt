package com.bselzer.kotlin.extension.common.collection

/**
 * @return whether or not the item is one of the items in the collection
 */
fun <T> T.isOneOf(vararg items: T?): Boolean
{
    return items.contains(this)
}