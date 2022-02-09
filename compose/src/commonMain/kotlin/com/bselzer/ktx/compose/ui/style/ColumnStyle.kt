package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bselzer.ktx.function.objects.merge

/**
 * CompositionLocal containing the preferred ColumnStyle that will be used by Column components by default.
 */
val LocalColumnStyle: ProvidableCompositionLocal<ColumnStyle> = compositionLocalOf(structuralEqualityPolicy()) { ColumnStyle.Default }

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
    verticalArrangement = style.verticalArrangement ?: Arrangement.Top,
    horizontalAlignment = style.horizontalAlignment ?: Alignment.Start,
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
    val verticalArrangement: Arrangement.Vertical? = null,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal? = null,
): ModifiableStyle<ColumnStyle> {
    companion object {
        @Stable
        val Default = ColumnStyle()
    }

    override fun merge(other: ColumnStyle?): ColumnStyle = if (other == null) this else ColumnStyle(
        modifier = modifier.then(other.modifier),
        verticalArrangement = verticalArrangement.merge(other.verticalArrangement),
        horizontalAlignment = horizontalAlignment.merge(other.horizontalAlignment)
    )
}