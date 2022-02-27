package com.bselzer.ktx.compose.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.style.Icon
import com.bselzer.ktx.compose.ui.style.IconStyle
import com.bselzer.ktx.compose.ui.style.LocalIconStyle
import com.bselzer.ktx.compose.ui.style.localized
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Lays out an up directional icon.
 *
 * @param style the style describing how to lay out the icon
 */
@Composable
fun UpIcon(style: IconStyle = LocalIconStyle.localized()) = Icon(
    imageVector = Icons.Filled.KeyboardArrowUp,
    contentDescription = stringResource(Resources.strings.up),
    style = style,
)

/**
 * Lays out a down directional icon.
 *
 * @param style the style describing how to lay out the icon
 */
@Composable
fun DownIcon(style: IconStyle = LocalIconStyle.localized()) = Icon(
    imageVector = Icons.Filled.KeyboardArrowDown,
    contentDescription = stringResource(Resources.strings.down),
    style = style,
)

/**
 * Lays out a left directional icon.
 *
 * @param style the style describing how to lay out the icon
 */
@Composable
fun LeftIcon(style: IconStyle = LocalIconStyle.localized()) = Icon(
    imageVector = Icons.Filled.KeyboardArrowLeft,
    contentDescription = stringResource(Resources.strings.left),
    style = style,
)

/**
 * Lays out a right directional icon.
 *
 * @param style the style describing how to lay out the icon
 */
@Composable
fun RightIcon(style: IconStyle = LocalIconStyle.localized()) = Icon(
    imageVector = Icons.Filled.KeyboardArrowRight,
    contentDescription = stringResource(Resources.strings.right),
    style = style,
)