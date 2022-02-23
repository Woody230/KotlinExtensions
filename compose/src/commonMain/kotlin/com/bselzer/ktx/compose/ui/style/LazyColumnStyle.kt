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
 * CompositionLocal containing the preferred LazyColumnStyle that will be used by LazyColumn components by default.
 */
val LocalLazyColumnStyle: ProvidableCompositionLocal<LazyColumnStyle> = compositionLocalOf { LazyColumnStyle.Default }

/**
 * A wrapper around the standard [LazyColumn] composable.
 *
 * @param style the style describing how to lay out the [Column]
 * @param state The state object to be used to control or observe the list's state.
 * @param content the children to layout within the [Column]
 */
@Composable
fun LazyColumn(
    style: LazyColumnStyle = LocalLazyColumnStyle.localized(),
    state: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
) = androidx.compose.foundation.lazy.LazyColumn(
    modifier = style.modifier,
    state = state,
    contentPadding = style.contentPadding,
    reverseLayout = style.reverseLayout,
    verticalArrangement = style.verticalArrangement,
    horizontalAlignment = style.horizontalAlignment,
    flingBehavior = style.flingBehavior,
    content = content
)

/**
 * The style arguments associated with the [LazyColumn] composable.
 */
data class LazyColumnStyle(
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
     * The vertical arrangement of the layout's children.
     */
    val verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal = Alignment.Start,

    /**
     * Logic describing fling behavior.
     */
    val flingBehavior: FlingBehavior = DefaultFlingBehavior
): ModifiableStyle<LazyColumnStyle> {
    companion object {
        @Stable
        val Default = LazyColumnStyle()
    }

    override fun merge(other: LazyColumnStyle?): LazyColumnStyle = if (other == null) this else LazyColumnStyle(
        modifier = modifier.then(other.modifier),
        contentPadding = contentPadding.merge(other.contentPadding),
        reverseLayout = reverseLayout.safeMerge(other.reverseLayout, false),
        verticalArrangement = verticalArrangement.safeMerge(other.verticalArrangement, Arrangement.Top),
        horizontalAlignment = horizontalAlignment.safeMerge(other.horizontalAlignment, Alignment.Start),
        flingBehavior = flingBehavior.merge(other.flingBehavior)
    )

    @Composable
    override fun localized() = LazyColumnStyle(
        flingBehavior = ScrollableDefaults.flingBehavior()
    ).merge(this)
}