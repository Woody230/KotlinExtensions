package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred ColumnStyle that will be used by Column components by default.
 */
val LocalColumnStyle: StyleProvider<ColumnStyle> = StyleProvider(compositionLocalOf { ColumnStyle.Default })

/**
 * A wrapper around the standard [Column] composable.
 *
 * @param style the style describing how to lay out the [Column]
 * @param content the children to layout within the [Column]
 */
@Composable
fun Column(
    style: ColumnStyle = LocalColumnStyle.current,
    content: @Composable ColumnScope.() -> Unit
) = androidx.compose.foundation.layout.Column(
    modifier = style.modifier,
    verticalArrangement = style.verticalArrangement,
    horizontalAlignment = style.horizontalAlignment,
    content = content
)

/**
 * The style arguments associated with the [Column] composable.
 */
data class ColumnStyle(
    override val modifier: Modifier = Modifier,

    /**
     * The vertical arrangement of the layout's children.
     */
    val verticalArrangement: Arrangement.Vertical = Arrangement.Top,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) : ModifierStyle<ColumnStyle>() {
    companion object {
        @Stable
        val Default = ColumnStyle()
    }

    override fun safeMerge(other: ColumnStyle): ColumnStyle = ColumnStyle(
        modifier = modifier.then(other.modifier),
        verticalArrangement = verticalArrangement.safeMerge(other.verticalArrangement, Arrangement.Top),
        horizontalAlignment = horizontalAlignment.safeMerge(other.horizontalAlignment, Alignment.Start)
    )

    @Composable
    override fun localized() = this

    override fun modify(modifier: Modifier): ColumnStyle = copy(modifier = modifier)
}