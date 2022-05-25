package com.bselzer.ktx.compose.resource.strings

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.bselzer.ktx.compose.ui.intl.LocalLocale
import dev.icerock.moko.resources.desc.*
import java.util.*

internal actual class SystemStringLocalizer actual constructor() : StringLocalizer {
    private val context
        @Composable
        get() = run {
            val context = LocalContext.current
            val locale = Locale(LocalLocale.current.toLanguageTag())
            val configuration = context.resources.configuration.apply { setLocale(locale) }
            context.createConfigurationContext(configuration)
        }

    @Composable
    override fun localize(desc: StringDesc): String = when (desc) {
        is CompositionStringDesc -> {
            val localized = desc.args.associateWith { localize(it) }
            desc.args.joinToString(separator = desc.separator ?: "") { arg -> localized[arg] ?: "" }
        }
        is PluralFormattedStringDesc -> context.resources.getQuantityString(desc.pluralsRes.resourceId, desc.number, *(desc.args.localize()))
        is PluralStringDesc -> context.resources.getQuantityString(desc.pluralsRes.resourceId, desc.number)
        is RawStringDesc -> desc.string
        is ResourceFormattedStringDesc -> context.resources.getString(desc.stringRes.resourceId, *(desc.args.localize()))
        is ResourceStringDesc -> context.resources.getString(desc.stringRes.resourceId)
        else -> throw NotImplementedError("Unable to localize an unsupported StringDesc: $desc")
    }

    @Composable
    private fun List<Any>.localize(): Array<Any> = map { arg -> if (arg is StringDesc) localize(arg) else arg.toString() }.toTypedArray()
}