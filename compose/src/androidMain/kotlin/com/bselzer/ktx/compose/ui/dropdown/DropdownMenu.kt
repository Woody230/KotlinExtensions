package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy

/**
 * A wrapper around the [DropdownMenu] composable.
 *
 * @param expanded Whether the menu is currently open and visible to the user
 * @param onDismissRequest Called when the user requests to dismiss the menu, such as by tapping outside the menu's bounds
 * @param style the style describing how to lay out the dropdown menu
 * @param content the content to lay out inside the dropdown menu
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    style: DropdownStyle,
    content: @Composable ColumnScope.() -> Unit,
) = androidx.compose.material.DropdownMenu(
    expanded = expanded,
    onDismissRequest = onDismissRequest,
    modifier = style.modifier,
    offset = style.offset ?: DpOffset(0.dp, 0.dp),
    properties = PopupProperties(
        focusable = style.properties.focusable ?: false,
        dismissOnBackPress = style.properties.dismissOnBackPress ?: true,
        securePolicy = style.properties.securePolicy ?: SecureFlagPolicy.Inherit,
        excludeFromSystemGesture = style.properties.excludeFromSystemGesture ?: true,
        clippingEnabled = style.properties.clippingEnabled ?: true,
        usePlatformDefaultWidth = style.properties.usePlatformDefaultWidth ?: false
    ),
    content = content
)