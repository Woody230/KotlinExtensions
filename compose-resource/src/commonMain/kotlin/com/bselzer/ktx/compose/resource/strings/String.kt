package com.bselzer.ktx.compose.resource.strings

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

/**
 * Localizes the [StringDesc] using the current locale.
 */
@Composable
fun StringDesc.localized(): String = StringLocalizer.localize(this)

/**
 * Localizes the [StringResource] using the current locale.
 */
@Composable
fun StringResource.localized(): String = StringLocalizer.localize(desc())