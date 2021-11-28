package com.bselzer.library.kotlin.extension.compose.ui.picker

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.library.kotlin.extension.compose.ui.unit.toPx
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * @see <a href="https://gist.github.com/vganin/a9a84653a9f48a2d669910fbd48e32d5">gist by vganin for original logic</a>
 */

/**
 * Lays out a value picker.
 *
 * @param state the state of the value
 * @param values the possible state values
 * @param labels the displayable values for each of the [values]
 * @param modifier the value picker modifier
 * @param textStyle the style of the text for displaying the value
 * @param animationOffset the offset of the new value within the scrolling animation
 * @param upButton the block for displaying the up button
 * @param downButton the block for displaying the down button
 */
@Composable
fun <T> ValuePicker(
    modifier: Modifier = Modifier,
    state: MutableState<T>,
    values: List<T>,
    labels: List<String>,
    textStyle: TextStyle = LocalTextStyle.current,
    animationOffset: Dp = 18.dp,
    upButton: @Composable (onClick: () -> Unit) -> Unit = { UpButton(onClick = it) },
    downButton: @Composable (onClick: () -> Unit) -> Unit = { DownButton(onClick = it) }
) {
    val currentValue = state.value
    val currentIndex = values.indexOfFirst { value -> value == currentValue }

    PickerColumn(
        modifier = modifier,
        index = currentIndex,
        indexRange = values.indices,
        animationOffset = animationOffset,
        textStyle = textStyle,
        upButton = upButton,
        downButton = downButton,
        setState = { index -> values.getOrNull(index)?.let { value -> state.value = value } },
        label = { index -> labels.getOrNull(index) ?: "" }
    )
}

/**
 * Displays an up icon button.
 *
 * @param modifier the button modifier
 * @param onClick the on-click handler
 */
@Composable
fun UpButton(modifier: Modifier = Modifier, onClick: () -> Unit) = IconButton(modifier = modifier, onClick = onClick) {
    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Up")
}

/**
 * Displays a down icon button.
 *
 * @param modifier the button modifier
 * @param onClick the on-click handler
 */
@Composable
fun DownButton(modifier: Modifier = Modifier, onClick: () -> Unit) = IconButton(modifier = modifier, onClick = onClick) {
    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Down")
}

@Composable
internal fun PickerColumn(
    modifier: Modifier = Modifier,
    index: Int,
    animationOffset: Dp = 18.dp,
    indexRange: IntRange? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    upButton: @Composable (onClick: () -> Unit) -> Unit = { UpButton(onClick = it) },
    downButton: @Composable (onClick: () -> Unit) -> Unit = { DownButton(onClick = it) },
    setState: (Int) -> Unit,
    label: (Int) -> String,
) {
    val scope = rememberCoroutineScope()

    val animationOffsetPx = animationOffset.toPx()
    val animatable = animatedOffset(index = index, offset = animationOffsetPx, range = indexRange)
    fun animatedStateValue(offset: Float): Int = index - (offset / animationOffsetPx).toInt()

    Column(
        modifier = modifier
            .wrapContentSize()
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { deltaY ->
                    scope.launch {
                        animatable.snapTo(animatable.value + deltaY)
                    }
                },
                onDragStopped = { velocity ->
                    scope.launch {
                        val endValue = animatable.fling(
                            initialVelocity = velocity,
                            animationSpec = exponentialDecay(frictionMultiplier = 20f),
                            adjustTarget = { target ->
                                val coercedTarget = target % animationOffsetPx
                                val coercedAnchors = listOf(-animationOffsetPx, 0f, animationOffsetPx)
                                val coercedPoint = coercedAnchors.minByOrNull { abs(it - coercedTarget) }!!
                                val base = animationOffsetPx * (target / animationOffsetPx).toInt()
                                coercedPoint + base
                            }
                        ).endState.value

                        setState(animatedStateValue(endValue))
                        animatable.snapTo(0f)
                    }
                }
            )
    ) {
        upButton { setState(index + 1) }

        // Use the index associated with the animation when displaying the text values instead of the state index.
        PickerBox(index = animatedStateValue(animatable.value), animationOffset, animatable, textStyle) { selectedIndex -> label(selectedIndex) }

        downButton { setState(index - 1) }
    }
}

@Composable
internal fun ColumnScope.PickerBox(
    index: Int,
    animationOffset: Dp,
    animatable: Animatable<Float, AnimationVector1D>,
    textStyle: TextStyle = LocalTextStyle.current,
    label: (Int) -> String
) = Box(
    modifier = Modifier.align(Alignment.CenterHorizontally)
) {
    val textModifier = Modifier.align(Alignment.Center)
    val animationOffsetPx = animationOffset.toPx()
    val coercedOffset = animatable.value % animationOffsetPx
    ProvideTextStyle(textStyle) {
        Text(
            text = label(index - 1),
            modifier = textModifier
                .offset(y = -animationOffset)
                .alpha(coercedOffset / animationOffsetPx)
        )
        Text(
            text = label(index),
            modifier = textModifier.alpha(1 - abs(coercedOffset) / animationOffsetPx)
        )
        Text(
            text = label(index + 1),
            modifier = textModifier
                .offset(y = animationOffset)
                .alpha(-coercedOffset / animationOffsetPx)
        )
    }
}

@Composable
internal fun animatedOffset(index: Int, offset: Float, range: IntRange? = null): Animatable<Float, AnimationVector1D> = remember { Animatable(0f) }.apply {
    if (range != null) {
        val offsetRange = remember(index, range) {
            val first = -(range.last - index) * offset
            val last = -(range.first - index) * offset
            first..last
        }
        updateBounds(offsetRange.start, offsetRange.endInclusive)
    }
}

internal suspend fun Animatable<Float, AnimationVector1D>.fling(
    initialVelocity: Float,
    animationSpec: DecayAnimationSpec<Float>,
    adjustTarget: ((Float) -> Float)?,
    block: (Animatable<Float, AnimationVector1D>.() -> Unit)? = null,
): AnimationResult<Float, AnimationVector1D> {
    val targetValue = animationSpec.calculateTargetValue(value, initialVelocity)
    val adjustedTarget = adjustTarget?.invoke(targetValue)

    return if (adjustedTarget != null) {
        animateTo(
            targetValue = adjustedTarget,
            initialVelocity = initialVelocity,
            block = block
        )
    } else {
        animateDecay(
            initialVelocity = initialVelocity,
            animationSpec = animationSpec,
            block = block,
        )
    }
}