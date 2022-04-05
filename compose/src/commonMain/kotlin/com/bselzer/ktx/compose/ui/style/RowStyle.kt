package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred RowStyle that will be used by Row components by default.
 */
val LocalRowStyle: StyleProvider<RowStyle> = StyleProvider(compositionLocalOf { RowStyle.Default })

/**
 * A wrapper around the standard [Row] composable.
 *
 * @param style the style describing how to lay out the [Row]
 * @param content the children to layout within the [Row]
 */
@Composable
fun Row(
    style: RowStyle = LocalRowStyle.current,
    content: @Composable RowScope.() -> Unit
) = androidx.compose.foundation.layout.Row(
    modifier = style.modifier,
    horizontalArrangement = style.horizontalArrangement,
    verticalAlignment = style.verticalAlignment,
    content = content
)

/**
 * The style arguments associated with the [Row] composable.
 */
data class RowStyle(
    override val modifier: Modifier = Modifier,

    /**
     * The horizontal arrangement of the layout's children.
     */
    val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,

    /**
     * The vertical alignment of the layout's children.
     */
    val verticalAlignment: Alignment.Vertical = Alignment.Top,
): ModifiableStyle<RowStyle> {
    companion object {
        @Stable
        val Default = RowStyle()
    }

    override fun merge(other: RowStyle?): RowStyle = if (other == null) this else RowStyle(
        modifier = modifier.then(other.modifier),
        horizontalArrangement = horizontalArrangement.safeMerge(other.horizontalArrangement, Arrangement.Start),
        verticalAlignment = verticalAlignment.safeMerge(other.verticalAlignment, Alignment.Top)
    )

    @Composable
    override fun localized(): RowStyle = this
}