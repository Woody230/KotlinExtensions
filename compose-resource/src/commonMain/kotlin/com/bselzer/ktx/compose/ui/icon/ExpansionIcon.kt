package com.bselzer.ktx.compose.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.style.*
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource


/**
 * Lays out an icon for representing the expansion or compression of another element.
 *
 * @param isExpanded whether the element is expanded
 * @param style the style describing how to lay out the icon
 */
@Composable
fun ExpansionIcon(isExpanded: Boolean, style: IconStyle = LocalIconStyle.localized()) = Icon(
    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
    contentDescription = stringResource(resource = if (isExpanded) Resources.strings.condense else Resources.strings.expand),
    style = style
)

/**
 * Lays out an icon for representing the expansion or compression of another element.
 *
 * @param isExpanded whether the element is expanded
 * @param style the style describing how to lay out the button
 * @param iconStyle the style describing how to lay out the icon
 * @param setExpanded the block for setting the current state of expansion
 */
@Composable
fun ExpansionIconButton(
    isExpanded: Boolean,
    style: IconButtonStyle = LocalIconButtonStyle.localized(),
    iconStyle: IconStyle = LocalIconStyle.localized(),
    setExpanded: (Boolean) -> Unit,
) = IconButton(style = style, onClick = { setExpanded(!isExpanded) }) {
    ExpansionIcon(isExpanded = isExpanded,style = iconStyle)
}