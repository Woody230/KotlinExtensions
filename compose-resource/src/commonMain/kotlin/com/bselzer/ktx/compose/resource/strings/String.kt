package com.bselzer.ktx.compose.resource.strings

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.desc.StringDesc

/**
 * Localizes the [StringDesc] using the current locale.
 */
@Composable
fun StringDesc.localized(): String = StringLocalizer.localize(this)