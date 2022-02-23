package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred LazyRowStyle that will be used by LazyRow components by default.
 */
val LocalLazyRowStyle: ProvidableCompositionLocal<LazyRowStyle> = compositionLocalOf { LazyRowStyle.Default }

/**
 * A wrapper around the standard [LazyRow] composable.
 *
 * @param style the style describing how to lay out the [Row]
 * @param state The state object to be used to control or observe the list's state.
 * @param content the children to layout within the [Row]
 */
@Composable
fun LazyRow(
    style: LazyRowStyle = LocalLazyRowStyle.current,
    state: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
) = androidx.compose.foundation.lazy.LazyRow(
    modifier = style.modifier,
    state = state,
    contentPadding = style.contentPadding,
    reverseLayout = style.reverseLayout,
    horizontalArrangement = style.horizontalArrangement,
    verticalAlignment = style.verticalAlignment,
    flingBehavior = style.flingBehavior,
    content = content
)

/**
 * The style arguments associated with the [LazyRow] composable.
 */
data class LazyRowStyle(
    override val modifier: Modifier = Modifier,

    /**
     * A padding around the whole content. This will add padding for the. content after it has been clipped, which is not possible via modifier param.
     * You can use it to add a padding before the first item or after the last one. If you want to add a spacing between each item use verticalArrangement.
     */
    val contentPadding: PaddingValues = DefaultPadding,

    /**
     * Reverse the direction of scrolling and layout, when true items will be composed from the bottom to the top and LazyListState.firstVisibleItemIndex == 0 will mean we scrolled to the bottom.
     */
    val reverseLayout: Boolean = false,

    /**
     * The horizontal arrangement of the layout's children.
     */
    val horizontalArrangement: Arrangement.Horizontal = if (!reverseLayout) Arrangement.Start else Arrangement.End,

    /**
     * The vertical alignment of the layout's children.
     */
    val verticalAlignment: Alignment.Vertical = Alignment.Top,

    /**
     * Logic describing fling behavior.
     */
    val flingBehavior: FlingBehavior = DefaultFlingBehavior
): ModifiableStyle<LazyRowStyle> {
    companion object {
        @Stable
        val Default = LazyRowStyle()
    }

    override fun merge(other: LazyRowStyle?): LazyRowStyle = if (other == null) this else LazyRowStyle(
        modifier = modifier.then(other.modifier),
        contentPadding = contentPadding.merge(other.contentPadding),
        reverseLayout = reverseLayout.safeMerge(other.reverseLayout, false),
        horizontalArrangement = horizontalArrangement.safeMerge(other.horizontalArrangement, Arrangement.Start),
        verticalAlignment = verticalAlignment.safeMerge(other.verticalAlignment, Alignment.Top),
        flingBehavior = flingBehavior.merge(other.flingBehavior)
    )

    @Composable
    override fun localized(): LazyRowStyle = LazyRowStyle(
        flingBehavior = ScrollableDefaults.flingBehavior()
    ).merge(this)
}