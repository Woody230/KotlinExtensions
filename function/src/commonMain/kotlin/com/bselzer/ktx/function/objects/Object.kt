package com.bselzer.ktx.function.objects

/**
 * Merges this object with the other object by taking the [other] object if it is not the [default], otherwise taking this object.
 */
fun <T> T.safeMerge(other: T, default: T): T = if (other !== default) other else this

/**
 * Merges this object with the other object by taking the [other] object if it is not null, otherwise taking this object.
 */
fun <T> T?.nullMerge(other: T?): T? = other ?: this

/**
 * Takes this object if it is not the [default], otherwise takes the [other] object.
 */
fun <T> T.safeTake(other: T, default: T): T = if (this !== default) this else other