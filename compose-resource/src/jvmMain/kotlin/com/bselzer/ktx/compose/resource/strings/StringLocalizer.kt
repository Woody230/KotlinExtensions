package com.bselzer.ktx.compose.resource.strings

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.desc.StringDesc

internal actual class SystemStringLocalizer actual constructor() : StringLocalizer {
    @Composable
    override fun localize(desc: StringDesc): String = desc.localized()
}