package com.bselzer.ktx.compose.ui.picker

import androidx.compose.runtime.Composable

/**
 * @see <a href="https://gist.github.com/vganin/a9a84653a9f48a2d669910fbd48e32d5">gist by vganin for original logic</a>
 */

/**
 * Lays out a number picker.
 *
 * @param style the style describing how to lay out the picker
 * @param upIcon the block for displaying the up icon
 * @param downIcon the block for displaying the down icon
 * @param value the current number
 * @param range the range of the number
 * @param onStateChanged the callback for changes in the state
 */
@Composable
fun NumberPicker(
    style: PickerStyle = LocalPickerStyle.current,
    upIcon: @Composable () -> Unit,
    downIcon: @Composable () -> Unit,
    value: Int,
    range: IntRange? = null,
    onStateChanged: (Int) -> Unit,
) = PickerColumn(
    style = style,
    index = value,
    indexRange = range,
    upIcon = upIcon,
    downIcon = downIcon,
    setState = onStateChanged,
    label = { index -> index.toString() }
)