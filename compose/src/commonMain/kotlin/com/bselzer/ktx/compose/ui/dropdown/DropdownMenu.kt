package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable

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
    style: DropdownStyle, // TODO = LocalDropdownStyle.localized(), -- can't use defaults https://github.com/JetBrains/compose-jb/issues/1407#issuecomment-997854105
    content: @Composable ColumnScope.() -> Unit,
)