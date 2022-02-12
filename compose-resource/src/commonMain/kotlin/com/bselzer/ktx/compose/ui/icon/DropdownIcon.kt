package com.bselzer.ktx.compose.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import com.bselzer.ktx.compose.ui.dropdown.DropdownMenu
import com.bselzer.ktx.compose.ui.dropdown.DropdownStyle
import com.bselzer.ktx.compose.ui.dropdown.LocalDropdownStyle
import com.bselzer.ktx.compose.ui.style.*
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Lays out an icon for displaying a dropdown menu.
 *
 * @param style the style describing how to lay out the icon
 */
@Composable
fun DropdownIcon(style: IconStyle = LocalIconStyle.current) = Icon(
    imageVector = Icons.Filled.MoreVert,
    contentDescription = stringResource(resource = Resources.strings.more_options),
    style = style
)

/**
 * Lays out a button for displaying a dropdown menu.
 *
 * @param style the style describing how to lay out the button
 * @param iconStyle the style describing how to lay out the icon
 * @param setExpanded the block for setting the current state of expansion
 */
@Composable
fun DropdownIconButton(
    style: IconButtonStyle = LocalIconButtonStyle.current,
    iconStyle: IconStyle = LocalIconStyle.current,
    setExpanded: (Boolean) -> Unit,
) = IconButton(style = style, onClick = { setExpanded(true) }) {
    DropdownIcon(style = iconStyle)
}

/**
 * Lays out a dropdown menu for an app bar.
 *
 * @param initial whether the dropdown is initially expanded
 * @param style the style describing how to lay out the dropdown menu
 * @param buttonStyle the style describing how to lay out the dropdown button
 * @param iconStyle the style describing how to layout the dropdown button icon
 * @param icons the icons to lay out in the dropdown menu
 */
@Composable
fun DropdownMenuIcon(
    initial: Boolean = false,
    style: DropdownStyle = LocalDropdownStyle.current,
    buttonStyle: IconButtonStyle = LocalIconButtonStyle.current,
    iconStyle: IconStyle = LocalIconStyle.current,
    icons: (@Composable ((Boolean) -> Unit) -> Unit)?
) {
    var isExpanded by remember { mutableStateOf(initial) }
    icons?.let {
        androidx.compose.foundation.layout.Box {
            DropdownIconButton(style = buttonStyle, iconStyle = iconStyle) { isExpanded = it }
            DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }, style = style) {
                icons { isExpanded = it }
            }
        }
    }
}