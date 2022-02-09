package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.function.objects.merge

/**
 * CompositionLocal containing the preferred LazyRowStyle that will be used by LazyRow components by default.
 */
val LocalLazyRowStyle: ProvidableCompositionLocal<LazyRowStyle> = compositionLocalOf(structuralEqualityPolicy()) { LazyRowStyle.Default }

/**
 * A wrapper around the standard [LazyRow] composable.
 *
 * @param style the style describing how to lay out the [Row]
 * @param content the children to layout within the [Row]
 */
@Composable
fun LazyRow(
    style: LazyRowStyle = LocalLazyRowStyle.current,
    content: LazyListScope.() -> Unit
) {
    val reverseLayout = style.reverseLayout ?: false
    androidx.compose.foundation.lazy.LazyRow(
        modifier = style.modifier,
        state = style.state ?: rememberLazyListState(),
        contentPadding = style.contentPadding ?: PaddingValues(all = 0.dp),
        reverseLayout = reverseLayout,
        horizontalArrangement = style.horizontalArrangement ?: if (!reverseLayout) Arrangement.Start else Arrangement.End,
        verticalAlignment = style.verticalAlignment ?: Alignment.Top,
        flingBehavior = style.flingBehavior ?: ScrollableDefaults.flingBehavior(),
        content = content
    )
}

/**
 * The style arguments associated with the [LazyRow] composable.
 */
data class LazyRowStyle(
    override val modifier: Modifier = Modifier,

    /**
     * The state object to be used to control or observe the list's state.
     */
    val state: LazyListState? = null,

    /**
     * A padding around the whole content. This will add padding for the. content after it has been clipped, which is not possible via modifier param.
     * You can use it to add a padding before the first item or after the last one. If you want to add a spacing between each item use verticalArrangement.
     */
    val contentPadding: PaddingValues? = null,

    /**
     * Reverse the direction of scrolling and layout, when true items will be composed from the bottom to the top and LazyListState.firstVisibleItemIndex == 0 will mean we scrolled to the bottom.
     */
    val reverseLayout: Boolean? = null,

    /**
     * The horizontal arrangement of the layout's children.
     */
    val horizontalArrangement: Arrangement.Horizontal? = null,

    /**
     * The vertical alignment of the layout's children.
     */
    val verticalAlignment: Alignment.Vertical? = null,

    /**
     * Logic describing fling behavior.
     */
    val flingBehavior: FlingBehavior? = null
): ModifiableStyle<LazyRowStyle> {
    companion object {
        @Stable
        val Default = LazyRowStyle()
    }

    override fun merge(other: LazyRowStyle?): LazyRowStyle = if (other == null) this else LazyRowStyle(
        modifier = modifier.then(other.modifier),
        state = state.merge(other.state),
        contentPadding = contentPadding.merge(other.contentPadding),
        reverseLayout = reverseLayout.merge(other.reverseLayout),
        horizontalArrangement = horizontalArrangement.merge(other.horizontalArrangement),
        verticalAlignment = verticalAlignment.merge(other.verticalAlignment)
    )
}