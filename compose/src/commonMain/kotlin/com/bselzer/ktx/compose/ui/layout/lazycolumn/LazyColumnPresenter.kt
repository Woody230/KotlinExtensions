package com.bselzer.ktx.compose.ui.layout.lazycolumn

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class LazyColumnPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presentable] of the divider between items.
     */
    val divider: DividerPresenter = DividerPresenter.Default,

    /**
     * Whether a divider should be added before the items.
     */
    val prepend: TriState = ComposeMerger.triState.default,

    /**
     * Whether a divider should be added after the items.
     */
    val append: TriState = ComposeMerger.triState.default,

    /**
     * A padding around the whole content. This will add padding for the. content after it has been clipped, which is not possible via modifier param.
     * You can use it to add a padding before the first item or after the last one. If you want to add a spacing between each item use verticalArrangement.
     */
    val contentPadding: PaddingValues = ComposeMerger.paddingValues.default,

    /**
     * Reverse the direction of scrolling and layout, when true items will be composed from the bottom to the top and LazyListState.firstVisibleItemIndex == 0 will mean we scrolled to the bottom.
     */
    val reverseLayout: TriState = ComposeMerger.triState.default,

    /**
     * The vertical arrangement of the layout's children.
     */
    val verticalArrangement: Arrangement.Vertical = ComposeMerger.verticalArrangement.default,

    /**
     * The horizontal alignment of the layout's children.
     */
    val horizontalAlignment: Alignment.Horizontal = ComposeMerger.horizontalAlignment.default,

    /**
     * interaction describing fling behavior.
     */
    val flingBehavior: FlingBehavior = ComposeMerger.flingBehavior.default
) : Presenter<LazyColumnPresenter>(modifier) {
    companion object {
        @Stable
        val Default = LazyColumnPresenter()
    }

    override fun safeMerge(other: LazyColumnPresenter) = LazyColumnPresenter(
        modifier = modifier.merge(other.modifier),
        divider = divider.merge(other.divider),
        prepend = ComposeMerger.triState.safeMerge(prepend, other.prepend),
        append = ComposeMerger.triState.safeMerge(append, other.append),
        contentPadding = ComposeMerger.paddingValues.safeMerge(contentPadding, other.contentPadding),
        reverseLayout = ComposeMerger.triState.safeMerge(reverseLayout, other.reverseLayout),
        verticalArrangement = ComposeMerger.verticalArrangement.safeMerge(verticalArrangement, other.verticalArrangement),
        horizontalAlignment = ComposeMerger.horizontalAlignment.safeMerge(horizontalAlignment, other.horizontalAlignment),
        flingBehavior = ComposeMerger.flingBehavior.safeMerge(flingBehavior, other.flingBehavior)
    )

    @Composable
    override fun localized() = LazyColumnPresenter(
        prepend = TriState.FALSE,
        append = TriState.FALSE,
        contentPadding = PaddingValues(0.dp),
        reverseLayout = TriState.FALSE,
        horizontalAlignment = Alignment.Start,
        flingBehavior = ScrollableDefaults.flingBehavior()
    ).merge(this).run {
        copy(
            verticalArrangement = if (ComposeMerger.verticalArrangement.isDefault(verticalArrangement)) {
                if (!reverseLayout.toBoolean()) Arrangement.Top else Arrangement.Bottom
            } else {
                verticalArrangement
            }
        )
    }
}