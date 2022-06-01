package com.bselzer.ktx.compose.resource.ui.layout.text

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.resource.strings.localized
import com.bselzer.ktx.compose.ui.layout.text.textInteractor
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