package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.style.localized

/**
 * A wrapper around the [DropdownMenu] composable.
 *
 * @param expanded Whether the menu is currently open and visible to the user
 * @param onDismissRequest Called when the user requests to dismiss the menu, such as by tapping outside the menu's bounds
 * @param style the style describing how to lay out the dropdown menu
 * @param content the content to lay out inside the dropdown menu
 */
@Composable
expect fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    style: DropdownStyle = LocalDropdownStyle.localized(),
    content: @Composable ColumnScope.() -> Unit,
)