package com.bselzer.ktx.compose.resource.strings

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.desc.StringDesc

interface StringLocalizer {
    @Composable
    fun localize(desc: StringDesc): String

    companion object : StringLocalizer by SystemStringLocalizer()
}

internal expect class SystemStringLocalizer() : StringLocalizer