package com.bselzer.ktx.compose.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
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