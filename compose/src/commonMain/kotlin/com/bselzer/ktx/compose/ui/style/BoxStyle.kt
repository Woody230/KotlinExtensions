package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred BoxStyle that will be used by Box components by default.
 */
val LocalBoxStyle: StyleProvider<BoxStyle> = StyleProvider(compositionLocalOf { BoxStyle.Default })

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
) : ModifiableStyle<BoxStyle> {
    companion object {
        @Stable
        val Default = BoxStyle()
    }

    override fun merge(other: BoxStyle?): BoxStyle = if (other == null) this else BoxStyle(
        modifier = modifier.then(other.modifier),
        contentAlignment = contentAlignment.safeMerge(other.contentAlignment, Alignment.TopStart),
        propagateMinConstraints = propagateMinConstraints.safeMerge(other.propagateMinConstraints, false)
    )

    @Composable
    override fun localized(): BoxStyle = this
}