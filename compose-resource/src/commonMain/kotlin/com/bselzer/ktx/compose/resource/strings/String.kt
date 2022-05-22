package com.bselzer.ktx.compose.resource.strings

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

/**
 * Localizes the [StringDesc] using the current locale.
 */
@Composable
fun StringDesc.localized(): String = localized()

/**
 * Converts the [StringResource] into a [StringDesc] and localized the resource.
 */
@Composable
fun StringResource.localized() = desc().localized()