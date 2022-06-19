package com.bselzer.ktx.library

import android.content.Context
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.util.withContext

/**
 * The libraries from the plugin generated file at res/raw/aboutlibraries.json.
 */
fun Context.libraries(): List<Library> = Libs.Builder().withContext(this).build().libraries