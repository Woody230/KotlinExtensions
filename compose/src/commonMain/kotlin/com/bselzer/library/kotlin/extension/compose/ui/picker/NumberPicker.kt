package com.bselzer.library.kotlin.extension.compose.ui.picker

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @see <a href="https://gist.github.com/vganin/a9a84653a9f48a2d669910fbd48e32d5">gist by vganin for original logic</a>
 */

/**
 * Lays out a number picker.
 *
 * @param modifier the modifier applying to the number picker
 * @param textStyle the style of the text for displaying the number
 * @param animationOffset the offset of the new value within the scrolling animation
 * @param upButton the block for displaying the up button
 * @param downButton the block for displaying the down button
 * @param value the current number
 * @param range the range of the number
 * @param onStateChanged the callback for changes in the state
 */
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    animationOffset: Dp = 18.dp,
    upButton: @Composable (onClick: () -> Unit) -> Unit = { UpButton(onClick = it) },
    downButton: @Composable (onClick: () -> Unit) -> Unit = { DownButton(onClick = it) },
    value: Int,
    range: IntRange? = null,
    onStateChanged: (Int) -> Unit,
) = PickerColumn(
    modifier = modifier,
    index = value,
    indexRange = range,
    animationOffset = animationOffset,
    textStyle = textStyle,
    upButton = upButton,
    downButton = downButton,
    setState = onStateChanged,
    label = { index -> index.toString() }
)