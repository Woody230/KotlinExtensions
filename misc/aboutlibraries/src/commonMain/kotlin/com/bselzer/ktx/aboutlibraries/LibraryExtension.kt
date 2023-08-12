package com.bselzer.ktx.aboutlibraries

import com.mikepenz.aboutlibraries.entity.Library

/**
 * The developers of the library, or the organization if the developers do not exist.
 */
val Library.author: String
    get() = developers.takeIf { it.isNotEmpty() }?.map { it.name }?.joinToString(", ") ?: organization?.name ?: ""

/**
 * Removes duplicates of libraries with the same name, author, and artifact version.
 */
fun List<Library>.distinct() = distinctBy { library -> library.name.trim() + library.author.trim() + (library.artifactVersion ?: "").trim() }

/**
 * Sorts the libraries by their name lexicographically.
 */
fun List<Library>.sorted() = sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { library -> library.name })