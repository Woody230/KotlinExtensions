package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * CompositionLocal containing the preferred ColumnStyle that will be used by Column components by default.
 */
val LocalColumnStyle: ProvidableCompositionLocal<ColumnStyle> = compositionLocalOf { styleNotInitialized() }

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
 * Creates a localized [ColumnStyle].
 */
@Composable
fun columnStyle() = ColumnStyle()

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
): ModifiableStyle