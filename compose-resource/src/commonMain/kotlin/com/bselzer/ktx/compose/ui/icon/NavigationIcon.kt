package com.bselzer.ktx.compose.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.style.Icon
import com.bselzer.ktx.compose.ui.style.IconStyle
import com.bselzer.ktx.compose.ui.style.LocalIconStyle
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Lays out a navigation icon for going up the hierarchy.
 *
 * @param style the style describing how to lay out the icon
 */
@Composable
fun UpNavigationIcon(style: IconStyle = LocalIconStyle.current) = Icon(
    imageVector = Icons.Filled.ArrowBack,
    contentDescription = stringResource(resource = Resources.strings.up),
    style = style
)

/**
 * Lays out a navigation icon for showing a drawer.
 *
 * @param style the style describing how to lay out the icon
 */
@Composable
fun DrawerNavigationIcon(style: IconStyle = LocalIconStyle.current) = Icon(
    imageVector = Icons.Filled.Menu,
    contentDescription = stringResource(resource = Resources.strings.menu),
    style = style,
)