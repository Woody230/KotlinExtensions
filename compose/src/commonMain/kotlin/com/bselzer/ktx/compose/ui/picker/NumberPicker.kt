package com.bselzer.ktx.compose.ui.picker

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * @see <a href="https://gist.github.com/vganin/a9a84653a9f48a2d669910fbd48e32d5">gist by vganin for original logic</a>
 */

/**
 * Lays out a number picker.
 *
 * @param modifier the modifier applying to the number picker
 * @param textStyle the style of the text for displaying the number
 * @param animationOffset the offset of the new value within the scrolling animation
 * @param upIcon the block for displaying the up icon
 * @param downIcon the block for displaying the down icon
 * @param value the current number
 * @param range the range of the number
 * @param onStateChanged the callback for changes in the state
 */
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    animationOffset: Dp = DefaultAnimationOffset,
    upIcon: @Composable () -> Unit,
    downIcon: @Composable () -> Unit,
    value: Int,
    range: IntRange? = null,
    onStateChanged: (Int) -> Unit,
) = PickerColumn(
    modifier = modifier,
    index = value,
    indexRange = range,
    animationOffset = animationOffset,
    textStyle = textStyle,
    upIcon = upIcon,
    downIcon = downIcon,
    setState = onStateChanged,
    label = { index -> index.toString() }
)