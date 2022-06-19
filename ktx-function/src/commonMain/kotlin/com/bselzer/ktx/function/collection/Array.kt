package com.bselzer.ktx.function.collection

/**
 * @return whether the item exists and is one of the items in the collection
 */
fun <T> T.isOneOf(vararg items: T?): Boolean {
    this ?: return false
    return items.contains(this)
}

/**
 * Builds a new read-only List by populating a MutableList using the given builderAction and returning a read-only list with the same elements.
 * The list passed as a receiver to the builderAction is valid only inside that function. Using it outside of the function produces an unspecified behavior.
 * The returned list is serializable (JVM).
 *
 * The list is then converted into an array.
 */
inline fun <reified T> buildArray(builderAction: MutableList<T>.() -> Unit): Array<T> = buildList(builderAction).toTypedArray()