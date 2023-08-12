package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.runtime.Composable
import com.bselzer.ktx.resource.strings.localized
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc

/**
 * Localizes the [StringDesc] using the current locale and converts the text into an interactor.
 */
@Composable
fun StringDesc.textInteractor() = localized().textInteractor()

/**
 * Localizes the [StringResource] using the current locale and converts the text into an interactor.
 */
@Composable
fun StringResource.textInteractor() = localized().textInteractor()