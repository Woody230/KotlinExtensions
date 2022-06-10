package com.bselzer.ktx.compose.resource.strings

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.intl.LocalLocale
import dev.icerock.moko.resources.desc.*
import java.util.*

internal actual class SystemStringLocalizer actual constructor() : BaseSystemStringLocalizer() {
    @Composable
    override fun localize(desc: StringDesc): String {
        // Use the LocalLocale which allows for locally defining locales instead of just by a global which is what moko does.
        val locale = Locale(LocalLocale.current.toLanguageTag())
        return when (desc) {
            is CompositionStringDesc -> {
                val localized = desc.args.associateWith { localize(it) }
                desc.args.joinToString(separator = desc.separator ?: "") { arg -> localized[arg] ?: "" }
            }
            is PluralFormattedStringDesc -> desc.pluralsRes.localized(locale, desc.number, *(desc.args.localize()))
            is PluralStringDesc -> desc.pluralsRes.localized(locale, desc.number)
            is RawStringDesc -> desc.string
            is ResourceFormattedStringDesc -> desc.localized() // TODO stringRes and args are private so have to rely on moko
            is ResourceStringDesc -> desc.stringRes.localized(locale)
            else -> throw NotImplementedError("Unable to localize an unsupported StringDesc: $desc")
        }
    }
}