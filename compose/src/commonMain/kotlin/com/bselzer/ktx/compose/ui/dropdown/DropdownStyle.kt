package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.style.ModifiableStyle

/**
 * CompositionLocal containing the preferred DropdownStyle that will be used by Dropdown components by default.
 */
val LocalDropdownStyle: ProvidableCompositionLocal<DropdownStyle> = compositionLocalOf { Dropdown.DefaultStyle }

object Dropdown {
    @Stable
    val DefaultStyle = DropdownStyle()
}

/**
 * The style arguments associated with the [DropdownMenu] composable.
 */
expect class DropdownStyle(
    modifier: Modifier = Modifier,
): ModifiableStyle<DropdownStyle>