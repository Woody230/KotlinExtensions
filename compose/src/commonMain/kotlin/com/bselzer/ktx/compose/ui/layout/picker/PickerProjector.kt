package com.bselzer.ktx.compose.ui.layout.picker

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.icon.IconProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.TextProjector
import com.bselzer.ktx.compose.ui.unit.toPx
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Projects a value of type [T] with up and down directional buttons to change the selected value.
 */
class PickerProjector<T>(
    override val interactor: PickerInteractor<T>,
    override val presenter: PickerPresenter = PickerPresenter.Default
) : Projector<PickerInteractor<T>, PickerPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier ->
        PickerColumn(
            modifier = combinedModifier,
            index = interactor.selectedIndex,
            indexRange = interactor.range,
            setState = { index -> interactor.getValue(index)?.let { interactor.onSelectionChanged(it) } },
            label = { index -> interactor.getValue(index)?.let { interactor.getLabel(it) } ?: "" }
        )
    }

    /**
     * @see <a href="https://gist.github.com/vganin/a9a84653a9f48a2d669910fbd48e32d5">gist by vganin for original interaction</a>
     */
    @Composable
    private fun PickerColumn(
        modifier: Modifier,
        index: Int,
        indexRange: IntRange?,
        setState: (Int) -> Unit,
        label: (Int) -> String,
    ) {
        val scope = rememberCoroutineScope()

        val animationOffsetPx = presenter.animationOffset.toPx()
        val animatable = animatedOffset(index = index, offset = animationOffsetPx, range = indexRange)
        fun animatedStateValue(offset: Float): Int = index - (offset / animationOffsetPx).toInt()

        val columnModifier = Modifier
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
                                val coercedPoint = coercedAnchors.minByOrNull { abs(it - coercedTarget) } ?: 0f
                                val base = animationOffsetPx * (target / animationOffsetPx).toInt()
                                coercedPoint + base
                            }
                        ).endState.value

                        setState(animatedStateValue(endValue))
                        animatable.snapTo(0f)
                    }
                }
            )
            .then(modifier)

        Column(columnModifier) {
            IconButton(onClick = { setState(index + 1) }) {
                IconProjector(interactor.upIcon, presenter.icon).project()
            }

            // Use the index associated with the animation when displaying the text values instead of the state index.
            PickerBox(
                animatedStateValue(animatable.value),
                presenter.animationOffset,
                animatable,
            ) { selectedIndex ->
                label(selectedIndex)
            }

            IconButton(onClick = { setState(index - 1) }) {
                IconProjector(interactor.downIcon, presenter.icon).project()
            }
        }
    }

    @Composable
    private fun ColumnScope.PickerBox(
        index: Int,
        animationOffset: Dp,
        animatable: Animatable<Float, AnimationVector1D>,
        label: (Int) -> String
    ) = Box(
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        val textModifier = Modifier.align(Alignment.Center)
        val animationOffsetPx = animationOffset.toPx()
        val coercedOffset = animatable.value % animationOffsetPx

        TextProjector(
            interactor = TextInteractor(text = AnnotatedString(label(index - 1))),
            presenter = presenter.text
        ).project(
            modifier = textModifier
                .offset(y = -animationOffset)
                .alpha(coercedOffset / animationOffsetPx)
        )

        TextProjector(
            interactor = TextInteractor(text = AnnotatedString(label(index))),
            presenter = presenter.text
        ).project(
            modifier = textModifier.alpha(1 - abs(coercedOffset) / animationOffsetPx)
        )

        TextProjector(
            interactor = TextInteractor(text = AnnotatedString(label(index))),
            presenter = presenter.text
        ).project(
            modifier = textModifier
                .offset(y = animationOffset)
                .alpha(-coercedOffset / animationOffsetPx),
        )
    }

    @Composable
    private fun animatedOffset(index: Int, offset: Float, range: IntRange? = null): Animatable<Float, AnimationVector1D> = remember { Animatable(0f) }.apply {
        if (range != null) {
            val offsetRange = remember(index, range) {
                val first = -(range.last - index) * offset
                val last = -(range.first - index) * offset
                first..last
            }
            updateBounds(offsetRange.start, offsetRange.endInclusive)
        }
    }

    private suspend fun Animatable<Float, AnimationVector1D>.fling(
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
}