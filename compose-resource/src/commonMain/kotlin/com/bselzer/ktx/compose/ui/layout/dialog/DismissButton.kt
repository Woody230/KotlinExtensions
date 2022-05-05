package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.style.*
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Lays out a button for dismissing a dialog.
 *
 * @param style the style of the [Button]
 * @param textStyle the style of the text
 * @param onClick the on-click handler
 */
@Composable
fun DismissButton(
    style: ButtonStyle = LocalButtonStyle.current,
    textStyle: WordStyle = LocalWordStyle.current,
    onClick: () -> Unit
) = MaterialDialogButton(
    text = stringResource(Resources.strings.cancel),
    textStyle = textStyle,
    style = style,
    onClick = onClick
)