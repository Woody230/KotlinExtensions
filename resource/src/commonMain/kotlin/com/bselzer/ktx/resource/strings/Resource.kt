package com.bselzer.ktx.resource.strings

import com.bselzer.ktx.resource.KtxResources
import dev.icerock.moko.resources.StringResource

/**
 * [KtxResources.strings.enabled] if true, otherwise [KtxResources.strings.disabled]
 */
fun Boolean?.stringResource(): StringResource = if (this == true) KtxResources.strings.enabled else KtxResources.strings.disabled