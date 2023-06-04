package com.bselzer.ktx.resource.strings

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.desc.StringDesc

interface StringLocalizer {
    @Composable
    fun localize(desc: StringDesc): String

    companion object : StringLocalizer by SystemStringLocalizer()
}

internal abstract class BaseSystemStringLocalizer : StringLocalizer {
    @Composable
    protected fun List<Any>.localize(): Array<Any> = map { arg -> if (arg is StringDesc) localize(arg) else arg.toString() }.toTypedArray()
}

internal expect class SystemStringLocalizer() : BaseSystemStringLocalizer