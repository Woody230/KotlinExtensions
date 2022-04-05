package com.bselzer.ktx.compose.ui.dialog

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.style.*
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Lays out a button for confirmation a selection.
 *
 * @param style the style of the [Button]
 * @param textStyle the style of the text
 * @param onClick the on-click handler
 */
@Composable
fun ConfirmationButton(
    style: ButtonStyle = LocalButtonStyle.current,
    textStyle: WordStyle = LocalWordStyle.current,
    onClick: () -> Unit
) = MaterialDialogButton(text = stringResource(Resources.strings.ok), textStyle = textStyle, style = style, onClick = onClick)
