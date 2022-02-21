package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.PopupProperties

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
    offset = style.offset,
    properties = PopupProperties(
        focusable = style.properties.focusable,
        dismissOnBackPress = style.properties.dismissOnBackPress,
        dismissOnClickOutside = style.properties.dismissOnClickOutside,
        securePolicy = style.properties.securePolicy,
        excludeFromSystemGesture = style.properties.excludeFromSystemGesture,
        clippingEnabled = style.properties.clippingEnabled,
        usePlatformDefaultWidth = style.properties.usePlatformDefaultWidth
    ),
    content = content
)