package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.style.Icon
import com.bselzer.ktx.compose.ui.style.IconStyle
import com.bselzer.ktx.compose.ui.style.LocalIconStyle

import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Lays out a delete icon.
 *
 * @param style the style describing how to lay out the icon
 */
@Composable
fun DeleteIcon(style: IconStyle = LocalIconStyle.current) = Icon(
    imageVector = Icons.Filled.Delete,
    contentDescription = stringResource(resource = Resources.strings.delete),
    style = style
)