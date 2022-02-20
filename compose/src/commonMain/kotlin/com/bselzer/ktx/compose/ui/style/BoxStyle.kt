package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * CompositionLocal containing the preferred BoxStyle that will be used by Box components by default.
 */
val LocalBoxStyle: ProvidableCompositionLocal<BoxStyle> = compositionLocalOf { styleNotInitialized() }

/**
 * A wrapper around the standard [Box] composable.
 *
 * @param style the style describing how to lay out the Box
 * @param content the content to lay out inside the Box
 */
@Composable
fun Box(
    style: BoxStyle = LocalBoxStyle.current,
    content: @Composable BoxScope.() -> Unit
) = androidx.compose.foundation.layout.Box(
    modifier = style.modifier,
    contentAlignment = style.contentAlignment,
    propagateMinConstraints = style.propagateMinConstraints,
    content = content
)

/**
 * Creates a localized [BoxStyle].
 */
@Composable
fun boxStyle(): BoxStyle = BoxStyle()

/**
 * The style arguments associated with a [Box] composable.
 */
data class BoxStyle(
    override val modifier: Modifier = Modifier,

    /**
     * The default alignment inside the Box.
     */
    val contentAlignment: Alignment = Alignment.TopStart,

    /**
     * Whether the incoming min constraints should be passed to content.
     */
    val propagateMinConstraints: Boolean = false
) : ModifiableStyle