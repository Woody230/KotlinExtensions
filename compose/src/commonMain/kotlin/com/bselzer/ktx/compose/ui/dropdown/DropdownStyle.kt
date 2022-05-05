package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.style.ModifierStyle
import com.bselzer.ktx.compose.ui.style.StyleProvider.Companion.provider

/**
 * CompositionLocal containing the preferred DropdownStyle that will be used by Dropdown components by default.
 */
val LocalDropdownStyle: StyleProvider<DropdownStyle> = compositionLocalOf { Dropdown.Default }.provider()

object Dropdown {
    @Stable
    val Default = DropdownStyle()
}

/**
 * The style arguments associated with the [DropdownMenu] composable.
 */
expect class DropdownStyle(
    modifier: Modifier = Modifier,
) : ModifierStyle<DropdownStyle>