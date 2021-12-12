package com.bselzer.ktx.library

import com.mikepenz.aboutlibraries.entity.Library

/**
 * The developers of the library, or the organization if the developers do not exist.
 */
val Library.author: String
    get() = developers.takeIf { it.isNotEmpty() }?.map { it.name }?.joinToString(", ") ?: organization?.name ?: ""