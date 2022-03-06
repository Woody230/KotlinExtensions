package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonElevation
import androidx.compose.material.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.*
import com.bselzer.ktx.function.objects.safeMerge

/**
 * Takes the [other] color if it is specified, otherwise takes this color.
 */
fun Color.merge(other: Color) = if (other.isSpecified) other else this

/**
 * Takes the [other] unit if it is specified, otherwise takes this unit.
 */
fun TextUnit.merge(other: TextUnit) = if (other.isSpecified) other else this

/**
 * The default [Shape].
 */
val DefaultShape = RectangleShape

/**
 * Takes the [other] shape if it is not the [DefaultShape], otherwise takes this shape.
 */
fun Shape.merge(other: Shape): Shape = safeMerge(other, DefaultShape)

/**
 * The default [PaddingValues].
 */
val DefaultPadding = PaddingValues(all = 0.dp)

/**
 * Takes the [other] padding if it is not the [DefaultPadding], otherwise takes this padding.
 */
fun PaddingValues.merge(other: PaddingValues): PaddingValues = safeMerge(other, DefaultPadding)

/**
 * The default [ButtonElevation].
 */
val DefaultButtonElevation = object : ButtonElevation {
    @Composable
    override fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp> = mutableStateOf(0.dp)
}

/**
 * Takes the [other] elevation if it is not the [DefaultButtonElevation], otherwise takes this elevation.
 */
fun ButtonElevation.merge(other: ButtonElevation): ButtonElevation = safeMerge(other, DefaultButtonElevation)

/**
 * The default [ButtonColors].
 */
val DefaultButtonColors = object : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> = mutableStateOf(Color.Unspecified)

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> = mutableStateOf(Color.Unspecified)
}

/**
 * Takes the [other] colors if it is not the [DefaultButtonColors], otherwise takes these colors.
 */
fun ButtonColors.merge(other: ButtonColors): ButtonColors = safeMerge(other, DefaultButtonColors)

/**
 * The default [RadioButtonColors].
 */
val DefaultRadioButtonColors = object : RadioButtonColors {
    @Composable
    override fun radioColor(enabled: Boolean, selected: Boolean): State<Color> = mutableStateOf(Color.Unspecified)
}

/**
 * Takes the [other] colors if it is not the [DefaultRadioButtonColors], otherwise takes these colors.
 */
fun RadioButtonColors.merge(other: RadioButtonColors): RadioButtonColors = safeMerge(other, DefaultRadioButtonColors)

/**
 * The default [FlingBehavior].
 */
val DefaultFlingBehavior = object : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float = 0f
}

/**
 * Takes the [other] behavior if it is not the [DefaultFlingBehavior], otherwise takes this behavior.
 */
fun FlingBehavior.merge(other: FlingBehavior): FlingBehavior = safeMerge(other, DefaultFlingBehavior)

/**
 * The default [DpOffset].
 */
val DefaultDpOffset = DpOffset(0.dp, 0.dp)

/**
 * Takes the [other] offset if it is not the [DefaultDpOffset], otherwise takes this offset.
 */
fun DpOffset.merge(other: DpOffset): DpOffset = safeMerge(other, DefaultDpOffset)