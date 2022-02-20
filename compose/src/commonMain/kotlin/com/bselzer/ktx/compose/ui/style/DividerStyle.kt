package com.bselzer.ktx.compose.ui.style

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CompositionLocal containing the preferred DividerStyle that will be used by Divider components by default.
 */
val LocalDividerStyle: ProvidableCompositionLocal<DividerStyle> = compositionLocalOf { styleNotInitialized() }

/**
 * A wrapper around the standard [Divider] composable.
 *
 * @param style the style describing how to lay out the Divider
 */
@Composable
fun Divider(
    style: DividerStyle = LocalDividerStyle.current,
) = androidx.compose.material.Divider(
    modifier = style.modifier,
    color = style.color,
    thickness = style.thickness,
    startIndent = style.startIndent
)

/**
 * Creates a localized [DividerStyle].
 */
@Composable
fun dividerStyle() = DividerStyle(color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f))

/**
 * The style arguments associated with the [Divider] composable.
 */
data class DividerStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Color of the divider line.
     */
    val color: Color,

    /**
     * Thickness of the divider line, 1 dp is used by default. Using Dp.Hairline will produce a single pixel divider regardless of screen density.
     */
    val thickness: Dp = 1.dp,

    /**
     * Start offset of this line, no offset by default.
     */
    val startIndent: Dp = 0.dp,
): ModifiableStyle