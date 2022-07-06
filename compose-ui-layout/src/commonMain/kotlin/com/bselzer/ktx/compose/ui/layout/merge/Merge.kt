package com.bselzer.ktx.compose.ui.layout.merge

/**
 * Merges this object with the other object by taking the [other] object if it is not the [default], otherwise taking this object.
 */
internal fun <T> T.safeMerge(other: T, default: T): T = if (other != default) other else this