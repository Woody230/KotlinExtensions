package com.bselzer.ktx.compose.ui.style

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.unit.specified
import com.bselzer.ktx.function.objects.merge

/**
 * CompositionLocal containing the preferred DividerStyle that will be used by Divider components by default.
 */
val LocalDividerStyle: ProvidableCompositionLocal<DividerStyle> = compositionLocalOf { DividerStyle.Default }

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
    color = style.color ?: MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    thickness = (style.thickness ?: LocalThickness.current).specified(1.dp),
    startIndent = (style.startIndent ?: LocalIndent.current).specified(0.dp)
)

/**
 * The style arguments associated with the [Divider] composable.
 */
data class DividerStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Color of the divider line.
     */
    val color: Color? = null,

    /**
     * Thickness of the divider line, 1 dp is used by default. Using Dp.Hairline will produce a single pixel divider regardless of screen density.
     */
    val thickness: Dp? = null,

    /**
     * Start offset of this line, no offset by default.
     */
    val startIndent: Dp? = null,
): ModifiableStyle<DividerStyle> {
    companion object {
        @Stable
        val Default = DividerStyle()
    }

    override fun merge(other: DividerStyle?): DividerStyle = if (other == null) this else DividerStyle(
        modifier = modifier.then(other.modifier),
        color = color.merge(other.color),
    )
}